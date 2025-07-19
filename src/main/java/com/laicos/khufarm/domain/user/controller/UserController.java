package com.laicos.khufarm.domain.user.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.user.dto.response.UserValueResponse;
import com.laicos.khufarm.domain.user.service.UserQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping("/value")
    public BaseResponse<UserValueResponse> getUserValue(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        UserValueResponse userValueResponse = userQueryService.getUserValue(customUserDetails.getUser());

        return BaseResponse.onSuccess(userValueResponse);
    }
}
