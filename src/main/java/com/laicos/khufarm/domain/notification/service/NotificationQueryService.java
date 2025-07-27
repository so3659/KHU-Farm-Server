package com.laicos.khufarm.domain.notification.service;

import com.laicos.khufarm.domain.notification.dto.response.NotificationResponse;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationQueryService {

    Slice<NotificationResponse> getNotifications(User user, Long cursorId, Pageable pageable);
    NotificationResponse getNotification(User user, Long notificationId);
}
