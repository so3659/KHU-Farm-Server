package com.laicos.khufarm.domain.review.repository;

import com.laicos.khufarm.domain.review.entitiy.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, CustomReviewRepository {
}
