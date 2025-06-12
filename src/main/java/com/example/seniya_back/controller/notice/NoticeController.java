package com.example.seniya_back.controller.notice;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeCreateResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeDetailResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class NoticeController {
    @PostMapping
    public ResponseEntity<ResponseDto<NoticeDetailResponseDto>> createNotice(@Valid @RequestBody NoticeCreateResponseDto dto){
        return null;
    }
}
