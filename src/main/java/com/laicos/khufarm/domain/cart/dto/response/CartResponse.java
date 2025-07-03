package com.laicos.khufarm.domain.cart.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import lombok.*;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long userId;
    private Slice<FruitResponseWithCount> fruitsWithCount;
}
