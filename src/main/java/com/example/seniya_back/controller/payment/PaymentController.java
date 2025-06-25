package com.example.seniya_back.controller.payment;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.example.seniya_back.dto.payment.request.CreatePaymentRequestDto;
import com.example.seniya_back.dto.payment.response.GetAllPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.PaymentResponseDto;
import com.example.seniya_back.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.PAYMENT_API)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<ResponseDto<PaymentResponseDto>> createPayment(@AuthenticationPrincipal String username, @Valid @RequestBody CreatePaymentRequestDto dto) {
        ResponseDto<PaymentResponseDto> response = paymentService.createPayment(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<GetAllPaymentResponseDto>>> getAllPayments() {
        ResponseDto<List<GetAllPaymentResponseDto>> response = paymentService.getAllPayments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<ResponseDto<PaymentResponseDto>> confirmPayment(@PathVariable Long id, @RequestBody ConfirmPaymentRequestDto dto) {
        ResponseDto<PaymentResponseDto> response = paymentService.confirmPayment(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
