package com.example.seniya_back.repository;

import com.example.seniya_back.entity.TrainerProfile;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {
    TrainerProfile findByUser(User user);

//    @Query("SELECT e.course.trainerProfile, COUNT(e) as courseCount " +
//            "FROM Participations e " +
//            "GROUP BY e.course.trainerProfile " +
//            "ORDER BY courseCount DESC")
//    List<Object[]> findPopularTrainers(Pageable pageable);

}
