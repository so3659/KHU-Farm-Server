package com.laicos.khufarm.domain.auth.controller;

import com.laicos.khufarm.domain.user.dto.request.UserJoinRequest;
import com.laicos.khufarm.domain.user.dto.response.UserResponse;
import com.laicos.khufarm.domain.user.service.UserCommandService;
import com.laicos.khufarm.domain.user.service.UserQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @GetMapping("/checkExistId")
    public BaseResponse<Boolean> checkExistId(
            @RequestParam
            @Valid
            @NotBlank(message = "사용자 ID는 필수입니다.")
            @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "올바른 아이디 형식이 아닙니다")
            String userId
    ) {
        Boolean exists = userQueryService.checkExistId(userId);
        return BaseResponse.onSuccess(exists);
    }

    @PostMapping("/{userType}/signup")
    public BaseResponse<UserResponse> signup(
            @PathVariable("userType") Integer userType,
            @RequestBody UserJoinRequest userJoinRequest,
            HttpServletResponse response) {

        UserResponse userJoinResult = userCommandService.joinUser(userType, userJoinRequest, response);
        return BaseResponse.onSuccess(userJoinResult);
    }
}
