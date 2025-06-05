package com.example.seniya_back.controller.payment;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.payment.request.ConfirmPaymentRequestDto;
import com.example.seniya_back.dto.payment.request.CreatePaymentRequestDto;
import com.example.seniya_back.dto.payment.response.ConfirmPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.GetAllPaymentResponseDto;
import com.example.seniya_back.dto.payment.response.PaymentRespDto;
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
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<ResponseDto<PaymentRespDto>> createPayment(@AuthenticationPrincipal long id, @Valid @RequestBody CreatePaymentRequestDto dto) {
        ResponseDto<CreatePaymentRequestDto> response = paymentService.createPayment(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<GetAllPaymentResponseDto>>> getAllPayments() {
        ResponseDto<List<GetAllPaymentResponseDto>> response = paymentService.getAllPayments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ConfirmPaymentResponseDto>> confirmPayment(@PathVariable long id, @RequestBody ConfirmPaymentRequestDto dto) {
        ResponseDto<ConfirmPaymentResponseDto> response = paymentService.confirmPayment(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
