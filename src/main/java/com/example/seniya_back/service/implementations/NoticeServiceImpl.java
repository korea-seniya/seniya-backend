package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.uploadFile.TargetType;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.NoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import com.example.seniya_back.entity.Notice;
import com.example.seniya_back.entity.UploadFile;
import com.example.seniya_back.repository.NoticeRepository;
import com.example.seniya_back.repository.UploadFileRepository;
import com.example.seniya_back.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {
    private NoticeService noticeService;
    private NoticeRepository noticeRepository;
    private UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public ResponseDto<NoticeResponseDto> createNotice(NoticeCreateRequestDto dto, MultipartFile file) throws IOException {
        NoticeResponseDto responseDto = null;

        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        notice = noticeRepository.save(notice);

        if(file != null && !file.isEmpty()){
            saveFile(file, notice.getNoticeId(), TargetType.NOTICE);
        }

        responseDto = NoticeResponseDto.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }


    @Override
    public ResponseDto<NoticeDetailResponseDto> updateNotice(Long noticeId, NoticeUpdateRequestDto dto) {
        NoticeDetailResponseDto responseDto = null;

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + noticeId));

        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        Notice updatedNotice = noticeRepository.save(notice);

        responseDto = NoticeDetailResponseDto.builder()
                .noticeId(updatedNotice.getNoticeId())
                .title(updatedNotice.getTitle())
                .content(updatedNotice.getContent())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<?> deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + id));

        noticeRepository.delete(notice);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS).getBody();
    }
    
    @Override
    public ResponseDto<List<NoticeListResponseDto>> getAllNotices() {
        List<NoticeListResponseDto> responseDtos = null;

        List<Notice> notices = noticeRepository.findAll();

        responseDtos = notices.stream()
                .map(notice -> NoticeListResponseDto.builder()
                        .noticeId(notice.getNoticeId())
                        .title(notice.getTitle())
                        .createdAt(notice.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }



    private void saveFile(MultipartFile file, Long targetId, TargetType type) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String uuidName = UUID.randomUUID() + "_" + original;
        String fullPath = uploadDir + "/" + uuidName;
        file.transferTo(new File(fullPath));

        UploadFile uf = new UploadFile();
        uf.setOriginalName(original);
        uf.setFileName(uuidName);
        uf.setFilePath("/files/" + uuidName);
        uf.setFileSize(file.getSize());
        uf.setFileType(file.getContentType());
        uf.setTargetId(targetId);
        uf.setTargetType(type);

        uploadFileRepository.save(uf);
    }
}
