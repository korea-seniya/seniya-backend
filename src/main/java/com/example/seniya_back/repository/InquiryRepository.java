package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Inquiry;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> getInquiriesByUser(User user);
}
