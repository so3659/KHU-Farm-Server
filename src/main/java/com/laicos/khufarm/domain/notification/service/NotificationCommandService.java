package com.laicos.khufarm.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.laicos.khufarm.domain.notification.dto.request.FCMRequest;
import com.laicos.khufarm.domain.notification.dto.request.FCMTokenRequest;
import com.laicos.khufarm.domain.user.entity.User;

import java.io.IOException;

public interface NotificationCommandService {

    void sendMessage(Long userId, FCMRequest fcmRequest) throws FirebaseMessagingException;
    void saveToken(User user, FCMTokenRequest fcmTokenRequest) throws IOException;
}
