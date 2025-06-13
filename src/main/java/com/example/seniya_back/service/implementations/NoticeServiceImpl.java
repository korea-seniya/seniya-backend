package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.NoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import com.example.seniya_back.entity.Notice;
import com.example.seniya_back.repository.NoticeRepository;
import com.example.seniya_back.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {
    private NoticeService noticeService;
    private NoticeRepository noticeRepository;


    @Override
    public ResponseDto<NoticeResponseDto> createNotice(NoticeCreateRequestDto dto, MultipartFile file) throws IOException {
        NoticeResponseDto responseDto = null;

        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        notice = noticeRepository.save(notice);

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
}
