package com.laicos.khufarm.domain.order.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.order.dto.request.ShippingInfo;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;

    private List<FruitResponseWithCount> orderFruits;

    private ShippingInfo shippingInfo;

}
