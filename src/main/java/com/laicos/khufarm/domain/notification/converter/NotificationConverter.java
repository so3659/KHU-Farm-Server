package com.laicos.khufarm.domain.notification.converter;

import com.laicos.khufarm.domain.notification.dto.request.FCMRequest;
import com.laicos.khufarm.domain.notification.dto.response.NotificationResponse;
import com.laicos.khufarm.domain.notification.entitiy.Notification;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public static NotificationResponse toNotificationDto(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .build();
    }

    public static List<NotificationResponse> toNotificationDtoList(List<Notification> notifications) {
        return notifications.stream()
                .map(NotificationConverter::toNotificationDto)
                .toList();
    }
}
