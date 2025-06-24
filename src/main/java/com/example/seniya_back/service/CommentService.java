package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;
import com.example.seniya_back.dto.post.response.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentCreateResponseDto createComment(Long postId, CommentCreateRequestDto dto, String username);

    CommentUpdateResponseDto updateComment(Long postId, Long commentId, CommentUpdateRequestDto dto);

    void deleteComment(Long postId, Long commentId);

    ResponseDto<List<CommentResponseDto>> getComment(Long postId);
}