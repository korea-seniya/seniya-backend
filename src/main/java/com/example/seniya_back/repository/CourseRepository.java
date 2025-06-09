package com.example.seniya_back.repository;

import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.entity.Course;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<CourseResponseDto> findAllByUser(User user);
}
