package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequsetDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.dto.post.response.PostResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
   ResponseDto<PostResponseDto> createPost(@Valid PostCreateRequsetDto dto, MultipartFile file) throws IOException;

    ResponseDto<PostResponseDto> updatePost(Long id, @Valid PostUpdateRequestDto dto, MultipartFile file) throws IOException;

    ResponseDto<?> deletePost(Long id);

    ResponseDto<List<PostListResponseDto>> getAllPosts();

    ResponseDto<List<PostListResponseDto>> searchPostsByTitle(String title);

    ResponseDto<List<PostListResponseDto>> searchPostsByRole(String roleName);

 ResponseDto<PostDetailResponseDto> getPostById(Long id);
}