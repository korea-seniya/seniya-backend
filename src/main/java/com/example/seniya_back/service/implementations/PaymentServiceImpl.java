package com.example.seniya_back.service.implementations;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.example.seniya_back.dto.payment.request.CreatePaymentRequestDto;
import com.example.seniya_back.dto.payment.response.ConfirmPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.GetAllPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.PaymentRespDto;
import com.example.seniya_back.repository.PaymentRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<PaymentRespDto> createPayment(long id, CreatePaymentRequestDto dto) {
        PaymentRespDto respDto = null;



        return null;
    }

    @Override
    public ResponseDto<List<GetAllPaymentResponseDto>> getAllPayments() {

        return null;
    }

    @Override
    public ResponseDto<ConfirmPaymentResponseDto> confirmPayment(long id, ConfirmPaymentRequestDto dto) {

        return null;
    }
}
