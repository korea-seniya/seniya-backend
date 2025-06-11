//package com.example.seniya_back.controller.comment;
//
//import com.example.seniya_back.common.constants.ApiMappingPattern;
//import com.example.seniya_back.dto.ResponseDto;
//import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
//import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
//import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
//import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;
//import com.example.seniya_back.service.CommentService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(ApiMappingPattern.COMMENT_API)
//@RequiredArgsConstructor
//public class CommentController {
//    // 댓글
//    // : CUD
//
//    private final CommentService commentService;
//
//    // 1) 댓글 생성
//    @PostMapping
//    public ResponseEntity<ResponseDto<CommentCreateResponseDto>> createComment(
//            @PathVariable Long postId,
//            @Valid @RequestBody CommentCreateRequestDto dto
//    ) {
//        ResponseDto<CommentCreateResponseDto> response = commentService.createComment(postId, dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    // 2) 댓글 수정
//    @PutMapping("/{commentId}")
//    public ResponseEntity<ResponseDto<CommentUpdateResponseDto>> updateComment(
//            @PathVariable Long postId,
//            @PathVariable Long commentId,
//            @Valid @RequestBody CommentUpdateRequestDto dto
//    ) {
//        ResponseDto<CommentUpdateResponseDto> response = commentService.updateComment(postId, commentId, dto);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    // 3) 댓글 삭제
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<ResponseDto<Void>> deleteComment(
//            @PathVariable Long postId,
//            @PathVariable Long commentId
//    ) {
//        ResponseDto<Void> response = commentService.deleteComment(postId, commentId);
//        return ResponseEntity.noContent().build();
//    }
//}
