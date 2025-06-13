package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.GetNoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import com.example.seniya_back.entity.Notice;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.NoticeRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<NoticeResponseDto> createNotice(String username, NoticeCreateRequestDto dto) {
        NoticeResponseDto responseDto = null;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        String roleName = user.getRole().getRoleName();

        if(!roleName.equals("ADMIN")) {
            throw new IllegalArgumentException(ResponseMessage.NO_PERMISSION);
        }

        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        noticeRepository.save(notice);

        responseDto = NoticeResponseDto.builder()
                .username(notice.getUser().getName())
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }


    @Override // 수정
    public ResponseDto<GetNoticeDetailResponseDto> updateNotice(String username, Long id, NoticeUpdateRequestDto dto) {
        GetNoticeDetailResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        String roleName = user.getRole().getRoleName();

        if(!roleName.equals("ADMIN")) {
            throw new IllegalArgumentException(ResponseMessage.NO_PERMISSION);
        }

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NOTICE NOT FOUND"));

        if (dto.getTitle() != null) {
            notice.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null) {
            notice.setContent(dto.getContent());
        }

        Notice updatedNotice = noticeRepository.save(notice);

        responseDto = GetNoticeDetailResponseDto.builder()
                .username(notice.getUser().getName())
                .noticeId(updatedNotice.getNoticeId())
                .title(updatedNotice.getTitle())
                .content(updatedNotice.getContent())
                .createdAt(updatedNotice.getCreatedAt())
                .updatedAt(updatedNotice.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override // 삭제
    public ResponseDto<?> deleteNotice(String username, Long id) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        String roleName = user.getRole().getRoleName();

        if(!roleName.equals("ADMIN")) {
            throw new IllegalArgumentException(ResponseMessage.NO_PERMISSION);
        }
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NOTICE NOT FOUND"));

        noticeRepository.delete(notice);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS).getBody();
    }

    @Override // 전체 조회
    public ResponseDto<List<NoticeListResponseDto>> getAllNotices() {
        List<NoticeListResponseDto> responseDtos = null;

        List<Notice> notices = noticeRepository.findAll();

        responseDtos = notices.stream()
                .map(notice -> NoticeListResponseDto.builder()
                        .username(notice.getUser().getName())
                        .noticeId(notice.getNoticeId())
                        .title(notice.getTitle())
                        .createdAt(notice.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<GetNoticeDetailResponseDto> getNoticeById(Long id){
        GetNoticeDetailResponseDto responseDto = null;

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + id));

        responseDto = GetNoticeDetailResponseDto.builder()
                .username(notice.getUser().getName())
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();

    }

}
