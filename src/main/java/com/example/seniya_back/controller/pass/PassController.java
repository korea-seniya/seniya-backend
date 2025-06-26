package com.example.seniya_back.controller.pass;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.pass.response.PassResponseDto;
import com.example.seniya_back.service.PassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.PASS_API)
public class PassController {
    private final PassService passService;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<List<PassResponseDto>>> getMyPasses(
            @AuthenticationPrincipal String username
    ) {
        ResponseDto<List<PassResponseDto>> myPasses = passService.getMyValidPasses(username);
        return ResponseEntity.status(HttpStatus.OK).body(myPasses);
    }
}
