package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.pass.response.PassResponseDto;

import java.util.List;

public interface PassService {
    ResponseDto<List<PassResponseDto>> getMyValidPasses(String username);
}
