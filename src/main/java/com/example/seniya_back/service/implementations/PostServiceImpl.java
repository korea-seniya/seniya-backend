package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.uploadFile.TargetType;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequsetDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.CommentResponseDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.dto.post.response.PostResponseDto;
import com.example.seniya_back.entity.Post;
import com.example.seniya_back.entity.UploadFile;
import com.example.seniya_back.repository.PostRepository;
import com.example.seniya_back.repository.UploadFileRepository;
import com.example.seniya_back.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResponseDto<PostResponseDto> createPost(PostCreateRequsetDto dto, List<MultipartFile> files) throws IOException{
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        post = postRepository.save(post);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    saveFile(file, post.getPostId(), TargetType.POST); // 내부에서 UploadFile에 post 연결 필수
                }
            }
        }

        List<UploadFile> uploadFiles = uploadFileRepository.findByTargetIdAndTargetType(post.getPostId(), TargetType.POST);
        List<String> imageUrls = uploadFiles.stream()
                .map(UploadFile::getUrl)
                .toList();

        PostResponseDto responseDto = PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrls(imageUrls)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<PostDetailResponseDto> updatePost(Long id, PostUpdateRequestDto dto, List<MultipartFile> files) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + id));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post = postRepository.save(post);

        List<UploadFile> existing = uploadFileRepository.findByTargetIdAndTargetType(id, TargetType.POST);
        for (UploadFile uf : existing) {
            new File(uploadDir + "/" + uf.getFileName()).delete();
            uploadFileRepository.delete(uf);
        }

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    saveFile(file, id, TargetType.POST);
                }
            }
        }

        List<UploadFile> uploadFiles = uploadFileRepository.findByTargetIdAndTargetType(post.getPostId(), TargetType.POST);
        List<String> imageUrls = uploadFiles.stream()
                .map(UploadFile::getUrl)
                .collect(Collectors.toList());

        PostDetailResponseDto responseDto = PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .username(post.getUser().getUsername())
                .content(post.getContent())
                .imageUrls(imageUrls)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream()
                        .map(comment -> PostDetailResponseDto.CommentDto.builder()
                                .commentId(comment.getCommentId())
                                .username(comment.getUser().getUsername())
                                .content(comment.getContent())
                                .createdAt(comment.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<?> deletePost(Long id) {
        List<UploadFile> existing = uploadFileRepository.findByTargetIdAndTargetType(id, TargetType.POST);
        for (UploadFile uf : existing) {
            new File(uploadDir + "/" + uf.getFileName()).delete();
            uploadFileRepository.delete(uf);
        }

        postRepository.deleteById(id);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS).getBody();
    }

    @Override
    public ResponseDto<List<PostListResponseDto>> getAllPosts() {
        List<PostListResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findAll();

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .username(post.getUser().getUsername())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<PostDetailResponseDto> getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + id));

        List<String> imageUrls = post.getImages().stream()
                .map(UploadFile::getUrl) // 실제 경로 반환하는 메서드로 변경 가능
                .collect(Collectors.toList());

        List<PostDetailResponseDto.CommentDto> commentDtos = post.getComments().stream()
                .map(comment -> PostDetailResponseDto.CommentDto.builder()
                        .commentId(comment.getCommentId())
                        .username(comment.getUser().getUsername())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        PostDetailResponseDto responseDto = PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .imageUrls(imageUrls)
                .comments(commentDtos)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<List<PostListResponseDto>> searchByTitle(String title) {
        List<PostListResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findByTitleContaining(title);

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .username(post.getUser().getUsername())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<List<PostListResponseDto>> searchByRole(String roleName) {
        List<Post> posts = postRepository.findByUserRoleRoleName(roleName);

        List<PostListResponseDto> responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .username(post.getUser().getUsername())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    private void saveFile(MultipartFile file, Long targetId, TargetType type) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String uuidName = UUID.randomUUID() + "_" + original;
        String fullPath = uploadDir + "/" + uuidName;
        file.transferTo(new File(fullPath));

        UploadFile uf = new UploadFile();
        uf.setOriginalName(original);
        uf.setFileName(uuidName);
        uf.setFilePath("/files/" + uuidName);
        uf.setFileSize(file.getSize());
        uf.setFileType(file.getContentType());
        uf.setTargetId(targetId);
        uf.setTargetType(type);

        uploadFileRepository.save(uf);
    }
}
