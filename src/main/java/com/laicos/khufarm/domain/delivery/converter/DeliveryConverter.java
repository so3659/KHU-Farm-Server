package com.laicos.khufarm.domain.delivery.converter;

import com.laicos.khufarm.domain.delivery.dto.response.DeliveryInfoConfirmResponse;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryStatus;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class DeliveryConverter {

    public static DeliveryInfoConfirmResponse toDeliveryInfoConfirmResponse(
            OrderResponse orderResponse,
            DeliveryStatus deliveryStatus,
            OrderDetail orderDetail) {
        return DeliveryInfoConfirmResponse.builder()
                .orderResponse(orderResponse)
                .deliveryStatus(deliveryStatus)
                .deliveryCompany(orderDetail.getDeliveryCompany().getName())
                .deliveryNumber(orderDetail.getDeliveryNumber())
                .build();
    }
}
