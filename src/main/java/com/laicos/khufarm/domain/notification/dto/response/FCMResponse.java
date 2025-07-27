package com.laicos.khufarm.domain.notification.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FCMResponse {
    private boolean validateOnly;
    private FCMResponse.Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private FCMResponse.Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
