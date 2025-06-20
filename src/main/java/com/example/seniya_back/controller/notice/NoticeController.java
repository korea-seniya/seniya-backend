package com.example.seniya_back.controller.notice;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.GetNoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import com.example.seniya_back.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.seniya_back.common.constants.ApiMappingPattern.NOTICE_API;

@RestController
@RequestMapping(NOTICE_API)
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 작성
    @PostMapping
    public ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
            @Valid @RequestBody NoticeCreateRequestDto dto
    ) {
        ResponseDto<NoticeResponseDto> response = noticeService.createNotice( dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<GetNoticeDetailResponseDto>> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody NoticeUpdateRequestDto dto
    ){
        ResponseDto<GetNoticeDetailResponseDto> response = noticeService.updateNotice(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deleteNotice(
            @PathVariable Long id
    ) {
        ResponseDto<?> response = noticeService.deleteNotice(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지사항 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<NoticeListResponseDto>>> getAllNotices() {
        ResponseDto<List<NoticeListResponseDto>> response = noticeService.getAllNotices();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지사항 단권 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GetNoticeDetailResponseDto>> getNoticeById(@PathVariable Long id) {
        ResponseDto<GetNoticeDetailResponseDto> response = noticeService.getNoticeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}