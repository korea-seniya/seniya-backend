package com.example.seniya_back.controller.inquiry;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.Inquiry.requestDto.InquiryReqestDto;
import com.example.seniya_back.dto.Inquiry.responseDto.AllInquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryByIdResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.MyInquiryResponseDto;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.INQUIRY_API)
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    // 문의 생성
    @PutMapping
    public ResponseEntity<ResponseDto<InquiryResponseDto>> createInquiry(
            @AuthenticationPrincipal String username,
            @Valid @RequestBody InquiryReqestDto dto
    ) {
        ResponseDto<InquiryResponseDto> inquiry = inquiryService.createInquiry(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inquiry);
    }

    // 내 문의 전체 조회
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<List<MyInquiryResponseDto>>> getMyInquiry(
            @AuthenticationPrincipal String username
    ) {
        ResponseDto<List<MyInquiryResponseDto>> inquiries = inquiryService.getMyInquiry(username);
        return ResponseEntity.ok(inquiries);
    }

    // 문의 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<AllInquiryResponseDto>>> getAllInquiry() {
        ResponseDto<List<AllInquiryResponseDto>> inquiries = inquiryService.getAllInquiry();
        return ResponseEntity.ok(inquiries);
    }

    // 문의 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<InquiryByIdResponseDto>> getInquiryDetail(
            @AuthenticationPrincipal String username,
            @PathVariable Long id
    ) throws AccessDeniedException {
        ResponseDto<InquiryByIdResponseDto> inquiry = inquiryService.getInquiryDetail(username, id);
        return ResponseEntity.ok(inquiry);
    }

    // 문의 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<InquiryResponseDto>> updateInquiry(
            @AuthenticationPrincipal String username,
            @PathVariable Long id,
            @Valid @RequestBody InquiryReqestDto dto
    ) {
        ResponseDto<InquiryResponseDto> inquiry = inquiryService.updateInquiry(username, id, dto);
        return ResponseEntity.ok(inquiry);
    }



}
