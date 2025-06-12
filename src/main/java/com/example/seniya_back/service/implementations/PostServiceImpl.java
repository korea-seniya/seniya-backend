package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.uploadFile.TargetType;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.post.request.PostCreateRequsetDto;
import com.example.seniya_back.dto.post.request.PostUpdateRequestDto;
import com.example.seniya_back.dto.post.response.PostDetailResponseDto;
import com.example.seniya_back.dto.post.response.PostListResponseDto;
import com.example.seniya_back.dto.post.response.PostResponseDto;
import com.example.seniya_back.entity.Post;
import com.example.seniya_back.entity.UploadFile;
import com.example.seniya_back.repository.PostRepository;
import com.example.seniya_back.repository.UploadFileRepository;
import com.example.seniya_back.service.PostService;
import jakarta.persistence.EntityNotFoundException;
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
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    @Transactional
    public ResponseDto<PostResponseDto> createPost(PostCreateRequsetDto dto, MultipartFile file) throws IOException{
        PostResponseDto resposneDto = null;

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        post = postRepository.save(post);

        if (file != null && !file.isEmpty()) {
            saveFile(file, post.getPostId(), TargetType.POST);
        }

        resposneDto = PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, resposneDto).getBody();
    }

    @Override
    public ResponseDto<PostResponseDto> updatePost(Long id, PostUpdateRequestDto dto, MultipartFile file) throws IOException {
        PostResponseDto responseDto = null;

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + id));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        if (file != null && !file.isEmpty()) {
            List<UploadFile> oldFiles = uploadFileRepository.findByTargetIdAndTargetType(id, TargetType.POST);
            for (UploadFile oldFile : oldFiles) {
                File actualFile = new File(uploadDir + "/" + oldFile.getFileName());
                if (actualFile.exists()) actualFile.delete();
                uploadFileRepository.delete(oldFile);
            }
            saveFile(file, id, TargetType.POST);
        }

        responseDto = PostResponseDto.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<?> deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND + id));

        postRepository.delete(post);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS).getBody();
    }

    @Override
    public ResponseDto<List<PostListResponseDto>> getAllPosts() {
        List<PostListResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findAll();

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .title(post.getTitle())
                        .username(post.getUser())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<PostDetailResponseDto> getPostById(Long id) {
        return null;
    }

    @Override
    public ResponseDto<List<PostListResponseDto>> searchPostsByTitle(String title) {
        List<PostListResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findByTitleIgnoreCaseContaining(title);

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .username(post.getUser())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<List<PostListResponseDto>> searchPostsByRole(String roleName) {
        List<PostListResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findByRoleIgnoreCaseContaining(roleName);

        responseDtos = posts.stream()
                .map(post -> PostListResponseDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .username(post.getUser())
                        .build())
                .collect(Collectors.toList());

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
