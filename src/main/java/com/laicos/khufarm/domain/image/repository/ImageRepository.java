package com.laicos.khufarm.domain.image.repository;

import com.laicos.khufarm.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByImageUrl(String imageUrl);

    List<Image> findByImageStatusAndCreatedAtBefore(String status, LocalDateTime createdAt);
}
