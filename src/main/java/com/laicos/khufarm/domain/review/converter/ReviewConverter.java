package com.laicos.khufarm.domain.review.converter;

import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter {

    public static Review toReview(User user, ReviewRequest reviewRequest, OrderDetail orderDetail) {
        return Review.builder()
                .rating(reviewRequest.getRating())
                .imageUrl(reviewRequest.getImageUrl())
                .title(reviewRequest.getTitle())
                .content(reviewRequest.getContent())
                .isAnswered(false)
                .isPointed(false)
                .user(user)
                .seller(orderDetail.getFruit().getSeller())
                .fruit(orderDetail.getFruit())
                .order(orderDetail.getOrder())
                .build();
    }
}
