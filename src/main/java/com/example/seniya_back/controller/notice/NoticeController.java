package com.example.seniya_back.controller.notice;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.NoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import com.example.seniya_back.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.NOTICE_API)
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 작성
    @PostMapping(consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
            @RequestPart("data") @Valid NoticeCreateRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<NoticeResponseDto> response = noticeService.createNotice(dto, file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지사항 수정
    @PutMapping("/{notices}")
    public ResponseEntity<ResponseDto<NoticeDetailResponseDto>> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody NoticeUpdateRequestDto dto
    ){
        ResponseDto<NoticeDetailResponseDto> response = noticeService.updateNotice(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 공지사항 삭제
    @DeleteMapping("/{notices}")
    public ResponseEntity<ResponseDto<?>> deleteNotice(@PathVariable Long id) {
        ResponseDto<?> response = noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<NoticeListResponseDto>>> getNoticeList() {
        ResponseDto<List<NoticeListResponseDto>> notices = noticeService.getAllNotices();
        return ResponseEntity.status(HttpStatus.OK).body(notices);
    }
}
