package com.laicos.khufarm.domain.notification.service;

import com.laicos.khufarm.domain.notification.converter.NotificationConverter;
import com.laicos.khufarm.domain.notification.dto.response.NotificationResponse;
import com.laicos.khufarm.domain.notification.entitiy.Notification;
import com.laicos.khufarm.domain.notification.repository.NotificationRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.NotificationErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService{

    private final NotificationRepository notificationRepository;

    @Override
    public Slice<NotificationResponse> getNotifications(User user, Long cursorId, Pageable pageable){
        return notificationRepository.getNotifications(user, cursorId, pageable);
    }

    @Override
    public NotificationResponse getNotification(User user, Long notificationId){
        Notification notification = notificationRepository.findByUserAndId(user, notificationId)
                .orElseThrow(() -> new RestApiException(NotificationErrorStatus.NOTIFICATION_NOT_FOUND));

        notification.markAsRead();

        return NotificationConverter.toNotificationDto(notification);
    }
}
