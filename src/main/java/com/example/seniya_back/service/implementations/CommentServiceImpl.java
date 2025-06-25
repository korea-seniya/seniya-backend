package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.comment.request.CommentCreateRequestDto;
import com.example.seniya_back.dto.comment.request.CommentUpdateRequestDto;
import com.example.seniya_back.dto.comment.response.CommentCreateResponseDto;
import com.example.seniya_back.dto.comment.response.CommentUpdateResponseDto;
import com.example.seniya_back.dto.post.response.CommentResponseDto;
import com.example.seniya_back.entity.Comment;
import com.example.seniya_back.entity.Post;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.CommentRepository;
import com.example.seniya_back.repository.PostRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentCreateResponseDto createComment(Long postId, CommentCreateRequestDto dto, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자가 존재하지 않습니다."));

        Comment newComment = Comment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .build();

        commentRepository.save(newComment);

        return new CommentCreateResponseDto(
                newComment.getCommentId(),
                post.getPostId(),
                user.getName(),
                newComment.getContent(),
                newComment.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public CommentUpdateResponseDto updateComment(Long postId, Long commentId, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        comment.updateContent(dto.getContent());

        return new CommentUpdateResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public ResponseDto<List<CommentResponseDto>> getComment(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findByPost_PostId(postId);

        List<CommentResponseDto> responseDtos = comments.stream()
                .map(CommentResponseDto::new) // 👈 생성자 활용
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }
}
