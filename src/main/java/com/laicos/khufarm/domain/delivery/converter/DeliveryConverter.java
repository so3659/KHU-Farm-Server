package com.laicos.khufarm.domain.delivery.converter;

import com.laicos.khufarm.domain.delivery.dto.response.DeliveryInfoConfirmResponse;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryStatus;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class DeliveryConverter {

    public static DeliveryInfoConfirmResponse toDeliveryInfoConfirmResponse(
            OrderResponse orderResponse,
            DeliveryStatus deliveryStatus,
            String deliveryNumber) {
        return DeliveryInfoConfirmResponse.builder()
                .orderResponse(orderResponse)
                .deliveryStatus(deliveryStatus)
                .deliveryNumber(deliveryNumber)
                .build();
    }
}
