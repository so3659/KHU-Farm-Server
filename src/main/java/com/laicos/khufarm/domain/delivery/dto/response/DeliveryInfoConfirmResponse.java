package com.laicos.khufarm.domain.delivery.dto.response;

import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeliveryInfoConfirmResponse {

    private OrderResponse orderResponse;
    private DeliveryStatus deliveryStatus;
    private String deliveryCompany;
    private String deliveryNumber;
}
