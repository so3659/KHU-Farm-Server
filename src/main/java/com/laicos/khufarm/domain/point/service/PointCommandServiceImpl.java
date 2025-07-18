package com.laicos.khufarm.domain.point.service;

import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.repository.ReviewRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.ReviewErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommandServiceImpl implements PointCommandService{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public void getReviewPoint(User user, Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RestApiException(ReviewErrorStatus.REVIEW_NOT_FOUND));

        review.setIsPointed(true);

        user.updateTotalPoint(2);
        userRepository.save(user);
    }
}
