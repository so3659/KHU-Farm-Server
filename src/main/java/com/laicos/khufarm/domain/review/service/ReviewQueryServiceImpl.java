package com.laicos.khufarm.domain.review.service;

import com.laicos.khufarm.domain.review.converter.ReviewConverter;
import com.laicos.khufarm.domain.review.converter.ReviewReplyConverter;
import com.laicos.khufarm.domain.review.dto.ReviewReadCondition;
import com.laicos.khufarm.domain.review.dto.response.MyReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewReplyResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.repository.ReviewRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.ReviewErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService{

    private final ReviewRepository reviewRepository;

    @Override
    public Slice<ReviewResponse> getAllReviews(Long cursorId, ReviewReadCondition reviewReadCondition, Pageable pageable){

        return reviewRepository.getAllReviews(cursorId, reviewReadCondition, pageable);
    }

    @Override
    public Slice<MyReviewResponse> getMyReviews(User user, Long cursorId, Pageable pageable){

        return reviewRepository.getMyReviews(user, cursorId, pageable);
    }

    @Override
    public ReviewResponse getMyReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RestApiException(ReviewErrorStatus.REVIEW_NOT_FOUND));

        ReviewReplyResponse reviewReplyResponse = review.getReviewReply() != null
                ? ReviewReplyConverter.toReviewReplyDTO(review.getReviewReply().getContent())
                : null;

        return ReviewConverter.toReviewDTO(review, reviewReplyResponse);
    }
}
