package com.laicos.khufarm.domain.review.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyReviewResponse {

    private FruitResponse fruitResponse;
    private Integer orderCount;
    private ReviewResponse reviewResponse;
}
