package com.example.seniya_back.service;

import com.example.seniya_back.dto.Inquiry.requestDto.InquiryAnswerRequestDto;
import com.example.seniya_back.dto.Inquiry.requestDto.InquiryRequestDto;
import com.example.seniya_back.dto.Inquiry.responseDto.AllInquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryByIdResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.MyInquiryResponseDto;
import com.example.seniya_back.dto.ResponseDto;
import jakarta.validation.Valid;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface InquiryService {
    ResponseDto<InquiryResponseDto> createInquiry(String username, @Valid InquiryRequestDto dto);

    ResponseDto<List<MyInquiryResponseDto>> getMyInquiry(String username);

    ResponseDto<List<AllInquiryResponseDto>> getAllInquiry();

    ResponseDto<InquiryByIdResponseDto> getInquiryDetail(String username, Long id)throws AccessDeniedException;

    ResponseDto<InquiryResponseDto> updateInquiry(String username, Long id, @Valid InquiryRequestDto dto);

    ResponseDto<?> deleteInquiry(String username, Long id);

    ResponseDto<InquiryByIdResponseDto> inquiryAnswer(String username, Long id, @Valid InquiryAnswerRequestDto dto);
}
