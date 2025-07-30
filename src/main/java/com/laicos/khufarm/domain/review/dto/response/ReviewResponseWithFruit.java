package com.laicos.khufarm.domain.review.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewResponseWithFruit {

    private Long reviewId;
    private String title;
    private String userId;
    private String content;
    private String imageUrl;
    private Integer rating;
    private Boolean isAnswered;
    private Boolean isPointed;
    private LocalDateTime createdAt;
    private ReviewReplyResponse reply;
    private FruitResponse fruitResponse;
}
