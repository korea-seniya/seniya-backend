package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.Inquiry.requestDto.InquiryAnswerRequestDto;
import com.example.seniya_back.dto.Inquiry.requestDto.InquiryRequestDto;
import com.example.seniya_back.dto.Inquiry.responseDto.AllInquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryByIdResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.InquiryResponseDto;
import com.example.seniya_back.dto.Inquiry.responseDto.MyInquiryResponseDto;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.entity.Inquiry;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.InquiryRepository;
import com.example.seniya_back.repository.TrainerProfileRepository;
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
    private final TrainerProfileRepository trainerProfileRepository;

    @Override
    public ResponseDto<InquiryResponseDto> createInquiry(String username, InquiryRequestDto dto) {
        InquiryResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Inquiry newInquiry = Inquiry.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .isPrivated(dto.getIsPrivated())
                .build();

        Inquiry saved = inquiryRepository.save(newInquiry);

        responseDto = InquiryResponseDto.builder()
                .inquiryId(saved.getInquiryId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .isPrivate(saved.getIsPrivated())
                .createdAt(saved.getCreatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<List<MyInquiryResponseDto>> getMyInquiry(String username) {
        List<MyInquiryResponseDto> resDtos = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        List<Inquiry> inquiries = inquiryRepository.getInquiriesByUser(user);

        resDtos = inquiries.stream()
                .map(inquiry -> MyInquiryResponseDto.builder()
                        .title(inquiry.getTitle())
                        .content(inquiry.getContent())
                        .response(inquiry.getResponse())
                        .isPrivated(inquiry.getIsPrivated())
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
                        .id(inquiry.getInquiryId())
                        .username(inquiry.getUser().getUsername())
                        .title(inquiry.getTitle())
                        .content(inquiry.getContent())
                        .isPrivated(inquiry.getIsPrivated())
                        .createdAt(inquiry.getCreatedAt())
                        .updatedAt(inquiry.getUpdatedAt())
                        .build()
        ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<InquiryByIdResponseDto> getInquiryDetail(String username, Long id) {
        InquiryByIdResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        String roleName = user.getRole().getRoleName();

        if (inquiry.getIsPrivated()) {
            if (!roleName.equals("TRAINER") && !roleName.equals("ADMIN")) {
                if(!inquiry.getUser().equals(user)){
                    throw new IllegalArgumentException(ResponseMessage.NO_PERMISSION);
                }
            }
        }

        responseDto = InquiryByIdResponseDto.builder()
                .title(inquiry.getTitle())
                .username(inquiry.getUser().getName())
                .trainerName(inquiry.getTrainer() != null ? inquiry.getTrainer().getUser().getName() : null)
                .content(inquiry.getContent())
                .response(inquiry.getResponse())
                .isPrivated(inquiry.getIsPrivated())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<InquiryByIdResponseDto> updateInquiry(String username, Long id, InquiryRequestDto dto) {
        InquiryByIdResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        if (!inquiry.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException(ResponseMessage.NO_PERMISSION);
        }

        if (inquiry.getResponse() != null) {
            throw new IllegalArgumentException(ResponseMessage.FAILED);
        }

        inquiry.setTitle(dto.getTitle());
        inquiry.setContent(dto.getContent());
        inquiry.setIsPrivated(dto.getIsPrivated());

        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        responseDto = InquiryByIdResponseDto.builder()
                .title(savedInquiry.getTitle())
                .username(user.getUsername())
                .trainerName(savedInquiry.getTrainer().getUser().getName())
                .content(savedInquiry.getContent())
                .response(savedInquiry.getResponse())
                .isPrivated(savedInquiry.getIsPrivated())
                .createdAt(savedInquiry.getCreatedAt())
                .updatedAt(savedInquiry.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<?> deleteInquiry(String username, Long id) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        boolean isAdmin = user.getRole().getRoleName().equals("ADMIN");

        if (!inquiry.getUser().getUsername().equals(user.getUsername()) && !isAdmin) {
            throw new AccessDeniedException(ResponseMessage.NO_PERMISSION);
        }

        inquiryRepository.delete(inquiry);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS).getBody();
    }

    @Override
    public ResponseDto<InquiryByIdResponseDto> inquiryAnswer(String username, Long id, InquiryAnswerRequestDto dto) {
        InquiryByIdResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("INQUIRY NOT FOUND"));

        boolean isAdmin = user.getRole().getRoleName().equals("ADMIN");
        boolean isTrainer = user.getRole().getRoleName().equals("TRAINER");

        if (!isTrainer && !isAdmin) {
            throw new AccessDeniedException(ResponseMessage.NO_PERMISSION);
        }

        inquiry.setResponse(dto.getResponse());

        inquiryRepository.save(inquiry);

        responseDto = InquiryByIdResponseDto.builder()
                .title(inquiry.getTitle())
                .username(inquiry.getUser().getUsername())
                .trainerName(user.getUsername())
                .content(inquiry.getContent())
                .response(inquiry.getResponse())
                .isPrivated(inquiry.getIsPrivated())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }
}
