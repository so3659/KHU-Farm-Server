package com.laicos.khufarm.domain.review.converter;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.review.dto.response.MyReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewReplyResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

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
                .orderDetail(orderDetail)
                .build();
    }

    public static ReviewResponse toReviewDTO(Review review, ReviewReplyResponse reply) {
        return ReviewResponse.builder()
                .title(review.getTitle())
                .userId(review.getUser().getUserId())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .reply(reply)
                .isAnswered(review.isAnswered())
                .build();
    }

    public static List<ReviewResponse> toReviewDTOList(List<Review> reviews, List<ReviewReplyResponse> replyList) {
        return IntStream.range(0, reviews.size())
                .mapToObj(i -> toReviewDTO(reviews.get(i), replyList.get(i)))
                .toList();
    }

    public static MyReviewResponse toMyReviewDTO(FruitResponse fruitResponse, Integer orderCount, ReviewResponse reviewResponse) {
        return MyReviewResponse.builder()
                .fruitResponse(fruitResponse)
                .orderCount(orderCount)
                .reviewResponse(reviewResponse)
                .build();
    }

    public static List<MyReviewResponse> toMyReviewDTOList(List<FruitResponse> fruitDTOList, List<Integer> orderCountList, List<ReviewResponse> reviewDTOList){
        return IntStream.range(0, fruitDTOList.size())
                .mapToObj(i -> toMyReviewDTO(fruitDTOList.get(i), orderCountList.get(i), reviewDTOList.get(i)))
                .toList();
    }
}
