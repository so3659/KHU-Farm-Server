package com.laicos.khufarm.domain.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserValueResponse {

    private Long userId;
    private String userName;
    private Integer totalPoint;
    private Integer totalDonation;
    private Integer totalPurchasePrice;
    private Integer totalPurchaseWeight;
    private Integer totalDiscountPrice;
}
