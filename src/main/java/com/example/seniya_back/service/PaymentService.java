package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.example.seniya_back.dto.payment.request.CreatePaymentRequestDto;
import com.example.seniya_back.dto.payment.response.ConfirmPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.GetAllPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.PaymentRespDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PaymentService {
    ResponseDto<PaymentRespDto> createPayment(long id, @Valid CreatePaymentRequestDto dto);

    ResponseDto<List<GetAllPaymentResponseDto>> getAllPayments();

    ResponseDto<ConfirmPaymentResponseDto> confirmPayment(long id, ConfirmPaymentRequestDto dto);
}
