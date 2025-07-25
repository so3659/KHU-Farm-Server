package com.laicos.khufarm.domain.review.service;

import com.laicos.khufarm.domain.review.dto.ReviewReadCondition;
import com.laicos.khufarm.domain.review.dto.response.MyReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewQueryService {

    Slice<ReviewResponse> getAllReviews(Long cursorId, ReviewReadCondition reviewReadCondition, Pageable pageable);
    Slice<MyReviewResponse> getMyReviews(User user, Long cursorId, Pageable pageable);
    ReviewResponse getMyReview(Long reviewId);
    Slice<ReviewResponse> getSellerReviews(User user, Long cursorId, Pageable pageable, ReviewReadCondition reviewReadCondition);
}
