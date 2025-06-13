package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.NoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface NoticeService {


    ResponseDto<NoticeResponseDto> createNotice(NoticeCreateRequestDto dto, MultipartFile file) throws IOException;

    ResponseDto<NoticeDetailResponseDto> updateNotice(Long noticeId, @Valid NoticeUpdateRequestDto dto);

    ResponseDto<?> deleteNotice(Long id);

    ResponseDto<List<NoticeListResponseDto>> getAllNotices();

}
