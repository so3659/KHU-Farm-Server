package com.laicos.khufarm.domain.review.converter;

import com.laicos.khufarm.domain.review.dto.response.ReviewReplyResponse;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.entitiy.ReviewReply;
import org.springframework.stereotype.Component;

@Component
public class ReviewReplyConverter {

    public static ReviewReply toReviewReply(String content, Review review) {
        return ReviewReply.builder()
                .content(content)
                .review(review)
                .build();
    }

    public static ReviewReplyResponse toReviewReplyDTO(String content){
        return ReviewReplyResponse.builder()
                .content(content)
                .build();
    }
}
