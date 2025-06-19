package com.laicos.khufarm.domain.seller.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import lombok.*;
import org.springframework.data.domain.Slice;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SellerResponseWithFruit {

    private Long id;
    private String brandName;
    private String title;
    private String description;
    private String imageUrl;
    private Long userId;
    private Slice<FruitResponse> fruits;
}
