package com.laicos.khufarm.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.laicos.khufarm.domain.notification.converter.NotificationConverter;
import com.laicos.khufarm.domain.notification.dto.request.FCMRequest;
import com.laicos.khufarm.domain.notification.dto.request.FCMTokenRequest;
import com.laicos.khufarm.domain.notification.repository.NotificationRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.NotificationErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationCommandServiceImpl implements NotificationCommandService{

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void sendMessage(User user, FCMRequest fcmRequest) throws FirebaseMessagingException {
        String token = user.getFcmToken();

        try {
            String message = FirebaseMessaging.getInstance().send(Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(fcmRequest.getTitle())
                            .setBody(fcmRequest.getBody())
                            .build())
                    .setToken(token)
                    .build());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase 메시지 전송 실패! Error Code: {}, Message: {}", e.getErrorCode(), e.getMessage(), e);
            throw new RestApiException(NotificationErrorStatus.NOTIFICATION_SEND_ERROR);
        }

        com.laicos.khufarm.domain.notification.entitiy.Notification notification = NotificationConverter.toNotification(user, fcmRequest);
        notificationRepository.save(notification);
    }

    @Override
    public void saveToken(User user, FCMTokenRequest fcmTokenRequest) throws IOException{
        user.updateFcmToken(fcmTokenRequest.getFcmToken());
        userRepository.save(user);
    }
}
