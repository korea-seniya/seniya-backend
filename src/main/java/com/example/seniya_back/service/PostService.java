package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequsetDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {
   ResponseDto<PostDetailResponseDto> createPost(@Valid PostCreateRequsetDto dto);

    ResponseDto<PostDetailResponseDto> updatePost(Long id, @Valid PostUpdateRequestDto dto);

    ResponseDto<?> deletePost(Long id);

    ResponseDto<List<PostListResponseDto>> getAllPosts();

    ResponseDto<List<PostListResponseDto>> searchPostsByTitle(String title);

    ResponseDto<List<PostListResponseDto>> searchPostsByRole(String roleName);
}