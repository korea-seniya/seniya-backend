package com.example.seniya_back.mapper;

import com.example.seniya_back.dto.trainer.responseDto.PopularTrainerResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PopularTrainerMapper {
    PopularTrainerResponseDto findPopularTrainer();
}
