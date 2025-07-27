package com.laicos.khufarm.domain.notification.repository;

import com.laicos.khufarm.domain.notification.entitiy.Notification;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, CustomNotificationRepository{
    Optional<Notification> findByUserAndId(User user, Long id);
}
