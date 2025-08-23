package com.laicos.khufarm.domain.delivery.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface DeliveryCommandService {

    void updateDeliveryStatus(User user, Long orderDetailId, DeliveryInfoRequest deliveryInfoRequest) throws FirebaseMessagingException;
    void handleDeliveryStatusCallback(String carrierId, String trackingNumber) throws FirebaseMessagingException;
}
