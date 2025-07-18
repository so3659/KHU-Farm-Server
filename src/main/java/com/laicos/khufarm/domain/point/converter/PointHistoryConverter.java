package com.laicos.khufarm.domain.point.converter;

import com.laicos.khufarm.domain.point.entity.PointHistory;
import com.laicos.khufarm.domain.point.enums.PointEventType;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PointHistoryConverter {

    public static PointHistory toPointHistory(
            Integer point,
            PointEventType pointEventType,
            Long eventId,
            User user) {
        return PointHistory.builder()
                .point(point)
                .pointEventType(pointEventType)
                .eventId(eventId)
                .user(user)
                .build();
    }
}
