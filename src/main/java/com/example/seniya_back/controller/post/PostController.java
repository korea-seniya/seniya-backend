package com.example.seniya_back.controller.post;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequestDto;
import com.example.seniya_back.dto.post.request.PostSearchByRoleRequestDto;
import com.example.seniya_back.dto.post.request.PostSearchByTitleRequestDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.dto.post.response.PostResponseDto;
import com.example.seniya_back.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal String username,
            @RequestPart(name = "data", required = false) PostCreateRequestDto dto,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) throws IOException {
        ResponseDto<PostResponseDto> response = postService.createPost(username, dto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 수정
    @PostMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<PostDetailResponseDto>> updatePost(
            @AuthenticationPrincipal String username,
            @PathVariable Long postId,
            @RequestPart(value = "data", required = false) PostUpdateRequestDto dto,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) throws IOException {
        ResponseDto<PostDetailResponseDto> response = postService.updatePost(username, postId, dto, files);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<?>> deletePost(
            @AuthenticationPrincipal String username,
            @PathVariable("postId") Long postId) {
        ResponseDto<?> response = postService.deletePost(username, postId);
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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 게시글 제목 검색
    @GetMapping("/search-by-title")
    public ResponseEntity<ResponseDto<List<PostListResponseDto>>> searchByTitle(@RequestParam String title) {
        ResponseDto<List<PostListResponseDto>> posts = postService.searchByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }


    @GetMapping("/search-by-role")
    public ResponseEntity<ResponseDto<List<PostListResponseDto>>> searchByRole(@RequestParam String roleName) {
        ResponseDto<List<PostListResponseDto>> posts = postService.searchByRole(roleName);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}
