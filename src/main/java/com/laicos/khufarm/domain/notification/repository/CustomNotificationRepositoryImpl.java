package com.laicos.khufarm.domain.notification.repository;

import com.laicos.khufarm.domain.notification.converter.NotificationConverter;
import com.laicos.khufarm.domain.notification.dto.response.NotificationResponse;
import com.laicos.khufarm.domain.notification.entitiy.Notification;
import com.laicos.khufarm.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.notification.entitiy.QNotification.notification;

@Repository
@RequiredArgsConstructor
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<NotificationResponse> getNotifications(User user, Long cursorId, Pageable pageable){

        List<Notification> notificationList = jpaQueryFactory.selectFrom(notification)
                .leftJoin(notification.user).fetchJoin()
                .where(
                        eqUserId(user),
                        ltCursorId(cursorId), // 커서 조건
                        isRead()
                )
                .orderBy(notification.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<NotificationResponse> content = NotificationConverter.toNotificationDtoList(notificationList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
    private BooleanExpression eqUserId(User user) {
        return (user == null) ? null : notification.user.id.eq(user.getId());
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        return (cursorId == null) ? null : notification.id.lt(cursorId);
    }

    private BooleanExpression isRead() {
        return notification.isRead.eq(false);
    }
}
