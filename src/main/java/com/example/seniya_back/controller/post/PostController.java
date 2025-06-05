package com.example.seniya_back.controller.post;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequsetDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<ResponseDto<PostDetailResponseDto>> createPost(@Valid @RequestBody PostCreateRequsetDto dto) {
        ResponseDto<PostDetailResponseDto> post = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<PostDetailResponseDto>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequestDto dto
    ){
        ResponseDto<PostDetailResponseDto> response = postService.updatePost(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deletePost(@PathVariable Long id) {
        ResponseDto<?> response = postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<PostListResponseDto>>> getPostList() {
        ResponseDto<List<PostListResponseDto>> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    // 권한 별로 게시글 검색


}
