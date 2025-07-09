package com.laicos.khufarm.domain.review.service;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.repository.OrderDetailRepository;
import com.laicos.khufarm.domain.review.converter.ReviewConverter;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.repository.ReviewRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.ReviewErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final FruitRepository fruitRepository;

    @Override
    public void addReview(User user, ReviewRequest reviewRequest, Long orderDetailId){

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_DETAIL_NOT_FOUND));

        if(orderDetail.isReviewed()){
            throw new RestApiException(ReviewErrorStatus.ALREADY_REVIEWED);
        }

        orderDetail.updateReviewStatus(true);

        Review review = ReviewConverter.toReview(user, reviewRequest, orderDetail);

        Fruit fruit = fruitRepository.findById(orderDetail.getFruit().getId())
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        fruit.updateRating(reviewRequest.getRating());

        reviewRepository.save(review);
    }
}
