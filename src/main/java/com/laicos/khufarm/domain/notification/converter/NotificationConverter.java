package com.laicos.khufarm.domain.notification.converter;

import com.laicos.khufarm.domain.notification.dto.request.FCMRequest;
import com.laicos.khufarm.domain.notification.entitiy.Notification;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter {

    public static Notification toNotification(User user, FCMRequest fcmRequest) {
        return Notification.builder()
                .title(fcmRequest.getTitle())
                .content(fcmRequest.getBody())
                .isRead(false)
                .user(user)
                .build();
    }
}
