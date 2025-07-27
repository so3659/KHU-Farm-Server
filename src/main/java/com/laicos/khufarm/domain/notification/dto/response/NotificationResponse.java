package com.laicos.khufarm.domain.notification.dto.response;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationResponse {

    private Long notificationId;
    private String title;
    private String content;
}
