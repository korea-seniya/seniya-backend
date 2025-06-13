package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    List<Post> findByTitleIgnoreCaseContaining(String title);
//
//    List<Post> findByRoleIgnoreCaseContaining(String roleName);

    List<Post> findByTitleIgnoreCaseContaining(String title);

}
