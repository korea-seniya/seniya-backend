package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;
import jakarta.validation.Valid;

public interface CommentService {
    ResponseDto<CommentCreateResponseDto> createComment(Long postId, @Valid CommentCreateRequestDto dto);
    ResponseDto<CommentUpdateResponseDto> updateComment(Long postId, Long commentId, @Valid CommentUpdateRequestDto dto);
    ResponseDto<Void> deleteComment(Long postId, Long commentId);
}
