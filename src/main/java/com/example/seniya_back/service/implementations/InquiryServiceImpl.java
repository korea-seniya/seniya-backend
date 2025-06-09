package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.Inquiry.requestDto.InquiryReqestDto;
import com.example.seniya_back.dto.Inquiry.responseDto.AllInquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryByIdResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.MyInquiryResponseDto;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.entity.Inquiry;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.InquiryRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.InquiryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<InquiryResponseDto> createInquiry(String username, InquiryReqestDto dto) {
        InquiryResponseDto responseDto = null;

        User user = userRepository.findByUserName(username).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND);
        }

        Inquiry newInquiry = Inquiry.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        Inquiry saved = inquiryRepository.save(newInquiry);

        responseDto = InquiryResponseDto.builder()
                .inquiryId(saved.getInquiryId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<List<MyInquiryResponseDto>> getMyInquiry(String username) {
        List<MyInquiryResponseDto> resDtos = null;

        User user = userRepository.findByUserName(username).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND);
        }

        List<Inquiry> inquiries = inquiryRepository.getInquiriesByUser(user);

        resDtos = inquiries.stream()
                .map(inquiry -> MyInquiryResponseDto.builder()
                        .title(inquiry.getTitle())
                        .content(inquiry.getContent()).response(inquiry.getResponse())
                        .createdAt(inquiry.getCreatedAt())
                        .updatedAt(inquiry.getUpdatedAt())
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, resDtos).getBody();
    }

    @Override
    public ResponseDto<List<AllInquiryResponseDto>> getAllInquiry() {
        List<AllInquiryResponseDto> responseDtos = null;

        List<Inquiry> inquiries = inquiryRepository.findAll();

        responseDtos = inquiries.stream().map(
                inquiry -> AllInquiryResponseDto.builder()
                        .username(inquiry.getUser().getUserName())
                        .title(inquiry.getTitle())
                        .content(inquiry.getContent())
                        .createdAt(inquiry.getCreatedAt())
                        .updatedAt(inquiry.getUpdatedAt())
                        .build()
        ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<InquiryByIdResponseDto> getInquiryDetail(String username, Long id) {
        InquiryByIdResponseDto responseDto = null;

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        boolean isOwner = inquiry.getUser().getUserName().equals(username);
        boolean role = "ADMIN".equals(user.getRole().getRoleName()) || "TRAINER".equals(user.getRole().getRoleName());

        if (!isOwner && !role) {
            throw new AccessDeniedException(ResponseMessage.NO_PERMISSION);
        }

        responseDto = InquiryByIdResponseDto.builder()
                .title(inquiry.getTitle())
                .userName(inquiry.getUser().getName())
                .trainerName(inquiry.getTrainer().getUser().getName())
                .content(inquiry.getContent())
                .response(inquiry.getResponse())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<InquiryByIdResponseDto> updateInquiry(String username, Long id, InquiryReqestDto dto) {
        InquiryByIdResponseDto responseDto = null;

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        if (!inquiry.getUser().getUserName().equals(user.getUserName())) {
            throw new AccessDeniedException(ResponseMessage.NO_PERMISSION);
        }

        inquiry.setTitle(dto.getTitle());
        inquiry.setContent(dto.getContent());

        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        responseDto = InquiryByIdResponseDto.builder()
                .title(savedInquiry.getTitle())
                .userName(user.getUserName())
                .trainerName(savedInquiry.getTrainer().getUser().getName())
                .content(savedInquiry.getContent())
                .response(savedInquiry.getResponse())
                .createdAt(savedInquiry.getCreatedAt())
                .updatedAt(savedInquiry.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<Void> deleteInquiry(String username, Long id) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        if (!inquiry.getUser().getUserName().equals(user.getUserName())) {
            throw new AccessDeniedException(ResponseMessage.NO_PERMISSION);
        }

        inquiryRepository.delete(inquiry);

        return null;
    }
}
