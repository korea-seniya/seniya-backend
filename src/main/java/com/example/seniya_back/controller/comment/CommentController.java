package com.example.seniya_back.controller.comment;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;
import com.example.seniya_back.dto.post.response.CommentResponseDto;
import com.example.seniya_back.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 1) 댓글 생성
    @PostMapping
    public ResponseEntity<ResponseDto<CommentCreateResponseDto>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequestDto dto,
            @AuthenticationPrincipal String username
    ) {
        CommentCreateResponseDto responseDto = commentService.createComment(postId, dto, username);
        return ResponseDto.success("SUCCESS", "댓글이 성공적으로 등록되었습니다.", responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CommentResponseDto>>> getComment(@PathVariable Long postId) {
        ResponseDto<List<CommentResponseDto>> comments = commentService.getComment(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }



    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<CommentUpdateResponseDto>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto dto
    ) {
        CommentUpdateResponseDto responseDto = commentService.updateComment(postId, commentId, dto);
        return ResponseDto.success("SUCCESS", "댓글이 성공적으로 수정되었습니다.", responseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return ResponseDto.success("SUCCESS", "댓글이 성공적으로 삭제되었습니다.");
    }
}
