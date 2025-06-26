package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.payment.Status;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.pass.response.PassResponseDto;
import com.example.seniya_back.entity.Pass;
import com.example.seniya_back.repository.PassRepository;
import com.example.seniya_back.service.PassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassServiceImpl implements PassService {
    private final PassRepository passRepository;

    @Override
    public ResponseDto<List<PassResponseDto>> getMyValidPasses(String username) {
        List<Pass> passes = passRepository.findAllByUserUsernameAndPaymentStatus(username, Status.SUCCESS);
        List<PassResponseDto> responseDtos = passes.stream()
                .map(PassResponseDto::fromEntity)
                .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }
}
