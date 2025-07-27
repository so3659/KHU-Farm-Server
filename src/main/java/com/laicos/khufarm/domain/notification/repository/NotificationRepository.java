package com.laicos.khufarm.domain.notification.repository;

import com.laicos.khufarm.domain.notification.entitiy.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
