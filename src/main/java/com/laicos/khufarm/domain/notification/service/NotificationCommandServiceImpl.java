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
import com.laicos.khufarm.global.common.exception.code.status.UserErrorStatus;
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
    public void sendMessage(Long userId, FCMRequest fcmRequest) throws FirebaseMessagingException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus.USER_NOT_FOUND));

        String token = user.getFcmToken();

        // 1. FCM 푸시 알림 전송 (토큰이 있을 때만 시도)
        if (token != null && !token.isBlank()) {
            try {
                Message message = Message.builder()
                        .setNotification(Notification.builder()
                                .setTitle(fcmRequest.getTitle())
                                .setBody(fcmRequest.getBody())
                                .build())
                        .setToken(token)
                        .build();

                FirebaseMessaging.getInstance().send(message);

                com.laicos.khufarm.domain.notification.entitiy.Notification notification = NotificationConverter.toNotification(user, fcmRequest);
                notificationRepository.save(notification);

            } catch (FirebaseMessagingException e) {
                log.error("Firebase 메시지 전송 실패! (UserId: {}) Error Code: {}, Message: {}",
                        userId, e.getErrorCode(), e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                log.error("FCM Message 빌드 실패! (UserId: {}) Message: {}", userId, e.getMessage(), e);
            }
        } else {
            // 토큰이 없는 사용자인 경우
            log.warn("FCM 토큰이 없는 사용자입니다. 푸시 알림을 생략합니다. (UserId: {})", userId);
        }
    }

    @Override
    public void saveToken(User user, FCMTokenRequest fcmTokenRequest) throws IOException{
        user.updateFcmToken(fcmTokenRequest.getFcmToken());
        userRepository.save(user);
    }
}
