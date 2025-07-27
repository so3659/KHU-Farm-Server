package com.laicos.khufarm.domain.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FCMRequest {

    @NotNull(message = "FCM 제목은 필수 입력값입니다.")
    private String title;

    @NotNull(message = "FCM 본문은 필수 입력값입니다.")
    private String body;
}
