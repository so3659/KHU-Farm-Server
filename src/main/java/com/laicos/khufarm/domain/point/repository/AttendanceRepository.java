package com.laicos.khufarm.domain.point.repository;

import com.laicos.khufarm.domain.point.entity.Attendance;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Attendance> findAllByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
}
