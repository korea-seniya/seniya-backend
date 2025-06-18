package com.example.seniya_back.service;

import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;

public interface CommentService {
    CommentCreateResponseDto createComment(Long postId, Long userId, CommentCreateRequestDto dto);

    CommentUpdateResponseDto updateComment(Long postId, Long commentId, CommentUpdateRequestDto dto);

    void deleteComment(Long postId, Long commentId);
}