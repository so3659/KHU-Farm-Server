package com.laicos.khufarm.domain.notification.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FCMTokenRequest {

    @NotNull(message = "FCM 토큰은 필수 입력값입니다.")
    private String fcmToken;
}
