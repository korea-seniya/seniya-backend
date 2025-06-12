package com.example.seniya_back.controller.post;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequsetDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.dto.post.response.PostResponseDto;
import com.example.seniya_back.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.POST_API)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(
            @RequestPart("data") @Valid PostCreateRequsetDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<PostResponseDto> response = postService.createPost(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 수정
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<PostResponseDto>> updatePost(
            @PathVariable Long postId,
            @RequestPart("data") @Valid PostUpdateRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<PostResponseDto> response = postService.updatePost(postId, dto, file);
        return ResponseEntity.ok(response);
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

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PostDetailResponseDto>> getPostById(@PathVariable Long id) {
        ResponseDto<PostDetailResponseDto> response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }


    // 게시글 제목 검색
    @GetMapping
    public ResponseEntity<ResponseDto<List<PostListResponseDto>>> searchPostsByTitle(@RequestParam String title) {
        ResponseDto<List<PostListResponseDto>> response = postService.searchPostsByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 작성자 권한 별 검색
    @GetMapping
    public ResponseEntity<ResponseDto<List<PostListResponseDto>>> searchPostsByUserRole(@RequestParam String roleName) {
        ResponseDto<List<PostListResponseDto>> response = postService.searchPostsByRole(roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
