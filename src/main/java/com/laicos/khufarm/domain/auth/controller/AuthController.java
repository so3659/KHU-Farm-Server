package com.laicos.khufarm.domain.auth.controller;

import com.laicos.khufarm.domain.auth.dto.AccessTokenResponse;
import com.laicos.khufarm.domain.auth.service.AuthCommandService;
import com.laicos.khufarm.domain.user.dto.request.IndividualUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.UserLoginRequest;
import com.laicos.khufarm.domain.user.dto.response.UserResponse;
import com.laicos.khufarm.domain.user.service.UserCommandService;
import com.laicos.khufarm.domain.user.service.UserQueryService;
import com.laicos.khufarm.domain.user.validator.annotation.ExistUserType;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final AuthCommandService authCommandService;

    @GetMapping("/checkExistId")
    public BaseResponse<Boolean> checkExistId(
            @RequestParam
            @Valid
            @NotBlank(message = "아이디는 필수 입력값입니다.")
            @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "올바른 아이디 형식이 아닙니다")
            String userId
    ) {
        Boolean exists = userQueryService.checkExistId(userId);
        return BaseResponse.onSuccess(exists);
    }

    @PostMapping("/individual/signup")
    public BaseResponse<UserResponse> individualSignup(
            @Valid @RequestBody IndividualUserJoinRequest individualUserJoinRequest,
            HttpServletResponse response) {

        UserResponse userJoinResult = userCommandService.joinIndividualUser(individualUserJoinRequest, response);
        return BaseResponse.onSuccess(userJoinResult);
    }

    @PostMapping("/business/signup")
    public BaseResponse<UserResponse> businessSignup(
            @Valid @RequestBody IndividualUserJoinRequest individualUserJoinRequest,
            HttpServletResponse response) {

        UserResponse userJoinResult = userCommandService.joinUser(individualUserJoinRequest, response);
        return BaseResponse.onSuccess(userJoinResult);
    }

    @PostMapping("/farmer/signup")
    public BaseResponse<UserResponse> farmerSignup(
            @Valid @RequestBody IndividualUserJoinRequest individualUserJoinRequest,
            HttpServletResponse response) {

        UserResponse userJoinResult = userCommandService.joinUser(individualUserJoinRequest, response);
        return BaseResponse.onSuccess(userJoinResult);
    }

    @PostMapping("/login")
    public BaseResponse<UserResponse> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest,
            HttpServletResponse response) {

        UserResponse userLoginResult = userCommandService.loginUser(userLoginRequest, response);
        return BaseResponse.onSuccess(userLoginResult);
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(
            @CookieValue(name = "refresh_token") String refreshToken,
            HttpServletResponse response) {

        authCommandService.logout(refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .path("/")
                .build();

        response.setHeader("Set-Cookie", refreshTokenCookie.toString());
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/reissue")
    public BaseResponse<AccessTokenResponse> reissueToken(
            @CookieValue(name = "refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        log.info("refreshToken: {}", refreshToken);
        return BaseResponse.onSuccess(authCommandService.reissueToken(refreshToken, response));
    }
}
