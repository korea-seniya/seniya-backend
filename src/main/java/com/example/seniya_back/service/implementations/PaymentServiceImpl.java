package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.payment.Status;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.example.seniya_back.dto.payment.request.CreatePaymentRequestDto;
import com.example.seniya_back.dto.payment.response.GetAllPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.PaymentResponseDto;
import com.example.seniya_back.entity.Payment;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.PassRepository;
import com.example.seniya_back.repository.PaymentRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PassRepository passRepository;

    @Override
    public ResponseDto<PaymentResponseDto> createPayment(String username, CreatePaymentRequestDto dto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Payment payment = Payment.builder()
                .user(user)
                .method(dto.getMethod())
                .couponCount(dto.getCouponCount())
                .amount(BigDecimal.valueOf(dto.getCouponCount() * 1000L))
                .status(Status.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponseDto respDto = null;

        respDto = PaymentResponseDto.builder()
                .paymentId(savedPayment.getPaymentId())
                .status(savedPayment.getStatus())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
    }

    @Override
    public ResponseDto<List<GetAllPaymentResponseDto>> getAllPayments() {
        List<GetAllPaymentResponseDto> respDtos = null;

        List<Payment> payments = paymentRepository.findAll();

        respDtos = payments.stream()
                .map(payment -> GetAllPaymentResponseDto.builder()
                        .paymentId(payment.getPaymentId())
                        .name(payment.getUser().getName())
                        .phone(payment.getUser().getPhone())
                        .amount(payment.getAmount())
                        .method(payment.getMethod())
                        .status(payment.getStatus())
                        .couponCount(payment.getCouponCount())
                        .createdAt(payment.getCreatedAt())
                        .updatedAt(payment.getUpdatedAt())
                        .build()
                ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDtos).getBody();
    }

    @Override
    public ResponseDto<PaymentResponseDto> confirmPayment(long id, ConfirmPaymentRequestDto dto) {
        PaymentResponseDto respDto = null;

        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND));

        payment.setStatus(dto.getStatus());
        Payment savedPayment = paymentRepository.save(payment);

        respDto = PaymentResponseDto.builder()
                .paymentId(savedPayment.getPaymentId())
                .status(savedPayment.getStatus())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
    }
}