package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String title);
    List<Post> findByUserRoleRoleName(String roleName);
    @Query("SELECT p FROM Post p LEFT JOIN p.comments c GROUP BY p.postId ORDER BY COUNT(c) DESC")
    List<Post> findTopPostsByCommentCount(Pageable pageable);
}
