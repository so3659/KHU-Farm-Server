package com.laicos.khufarm.domain.seller.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SellerResponse {

    private Long id;
    private String brand;
    private String title;
    private String description;
    private String imageUrl;
    private Long userId;
}
