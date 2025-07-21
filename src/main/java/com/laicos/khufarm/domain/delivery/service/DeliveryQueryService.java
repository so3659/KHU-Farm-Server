package com.laicos.khufarm.domain.delivery.service;

import com.laicos.khufarm.domain.delivery.dto.response.DeliveryInfoConfirmResponse;
import com.laicos.khufarm.domain.user.entity.User;

public interface DeliveryQueryService {

    DeliveryInfoConfirmResponse getDeliveryInfo(User user, Long orderId);
}
