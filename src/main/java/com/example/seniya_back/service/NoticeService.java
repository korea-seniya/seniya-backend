package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeCreateRequestDto;
import com.example.seniya_back.dto.notice.response.NoticeListResponseDto;
import com.example.seniya_back.dto.notice.request.NoticeUpdateRequestDto;
import com.example.seniya_back.dto.notice.response.GetNoticeDetailResponseDto;
import com.example.seniya_back.dto.notice.response.NoticeResponseDto;
import jakarta.validation.Valid;
import java.util.List;

public interface NoticeService {


    ResponseDto<NoticeResponseDto> createNotice(NoticeCreateRequestDto dto);

    ResponseDto<GetNoticeDetailResponseDto> updateNotice(Long id, @Valid NoticeUpdateRequestDto dto);

    ResponseDto<?> deleteNotice(Long id);

    ResponseDto<List<NoticeListResponseDto>> getAllNotices();

    // 단권 조회
    ResponseDto<GetNoticeDetailResponseDto> getNoticeById(Long id);
}