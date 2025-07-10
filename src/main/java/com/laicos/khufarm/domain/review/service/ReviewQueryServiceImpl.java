package com.laicos.khufarm.domain.review.service;

import com.laicos.khufarm.domain.review.dto.response.MyReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.review.repository.ReviewRepository;
import com.laicos.khufarm.domain.user.entity.User;
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
    public Slice<ReviewResponse> getAllReviews(Long cursorId, Long fruitId, Pageable pageable){

        return reviewRepository.getAllReviews(cursorId, fruitId, pageable);
    }

    @Override
    public Slice<MyReviewResponse> getMyReviews(User user, Long cursorId, Pageable pageable){

        return reviewRepository.getMyReviews(user, cursorId, pageable);
    }
}
