package com.laicos.khufarm.domain.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.notification.dto.request.FCMRequest;
import com.laicos.khufarm.domain.notification.dto.request.FCMTokenRequest;
import com.laicos.khufarm.domain.notification.service.NotificationCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Validated
@Tag(name = "Notification", description = "푸시 알림 관련 API")
public class NotificationController {

    private final NotificationCommandService notificationCommandService;

    @PostMapping("/send")
    public BaseResponse<Void> pushMessage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Validated FCMRequest fcmRequest) throws FirebaseMessagingException {
        notificationCommandService.sendMessage(customUserDetails.getUser(), fcmRequest);

        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/saveToken")
    public BaseResponse<Void> getToken(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody FCMTokenRequest fCMTokenRequest) throws IOException {
       notificationCommandService.saveToken(customUserDetails.getUser(), fCMTokenRequest);

        return BaseResponse.onSuccess(null);
    }
}
