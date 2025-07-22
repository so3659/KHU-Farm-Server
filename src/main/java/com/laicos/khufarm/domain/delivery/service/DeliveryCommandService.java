package com.laicos.khufarm.domain.delivery.service;

import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface DeliveryCommandService {

    void updateDeliveryStatus(User user, Long orderDetailId, DeliveryInfoRequest deliveryInfoRequest);
}
