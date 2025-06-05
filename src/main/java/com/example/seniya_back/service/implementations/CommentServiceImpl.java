package com.example.seniya_back.service.implementations;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;
import com.example.seniya_back.entity.Comment;
import com.example.seniya_back.entity.Post;
import com.example.seniya_back.repository.CommentRepository;
import com.example.seniya_back.repository.PostRepository;
import com.example.seniya_back.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public ResponseDto<CommentCreateResponseDto> createComment(Long postId, CommentCreateRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        Comment newComment = Comment.builder()
                .post(post)
                .content(dto.getContent())
                .build();

        commentRepository.save(newComment);

        CommentCreateResponseDto responseDto = new CommentCreateResponseDto("댓글이 성공적으로 등록되었습니다.");
        return new ResponseDto<>(true, "SUCCESS", responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<CommentUpdateResponseDto> updateComment(Long postId, Long commentId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);

        CommentUpdateResponseDto responseDto = new CommentUpdateResponseDto("댓글이 성공적으로 수정되었습니다.");
        return new ResponseDto<>(true, "SUCCESS", responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        commentRepository.delete(comment);

        return new ResponseDto<>(true, "SUCCESS", null);
    }
}
