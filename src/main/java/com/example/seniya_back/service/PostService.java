package com.example.seniya_back.service;

import com.example.seniya_back.dto.Inquiry.responseDto.InquiryResponseDto;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequestDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.dto.post.response.PostResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
   ResponseDto<PostResponseDto> createPost(String username, @Valid PostCreateRequestDto dto, List<MultipartFile> files) throws IOException;

    ResponseDto<PostDetailResponseDto> updatePost(String username, Long id, @Valid PostUpdateRequestDto dto, List<MultipartFile> files) throws IOException;

    ResponseDto<?> deletePost(String username, Long id);

    ResponseDto<List<PostListResponseDto>> getAllPosts();

    ResponseDto<PostDetailResponseDto> getPostById(Long id);

    ResponseDto<List<PostListResponseDto>> searchByTitle(String title);

 ResponseDto<List<PostListResponseDto>> searchByRole(String roleName);

// ResponseDto<PostResponseDto> createPost(String username, @Valid PostCreateRequestDto dto);
}