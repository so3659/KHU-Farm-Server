package com.laicos.khufarm.domain.auth.service;

import com.laicos.khufarm.domain.auth.dto.AccessTokenResponse;
import com.laicos.khufarm.domain.auth.entity.RefreshToken;
import com.laicos.khufarm.domain.auth.jwt.JwtTokenProvider;
import com.laicos.khufarm.domain.auth.repository.RefreshTokenRepository;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.AuthErrorStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService{

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AccessTokenResponse reissueToken(String oldRefreshToken, HttpServletResponse response) {
        log.info("Client refresh token: {}", oldRefreshToken);
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(oldRefreshToken)
                .orElseThrow(() -> {
                    log.warn("DB에 저장된 refresh token과 일치하는 값이 없습니다.");
                    return new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN);
                });
        log.info("DB refresh token for member {}: {}", refreshTokenEntity.getUser().getId(),
                refreshTokenEntity.getToken());

        // Refresh Token에 해당하는 회원 조회
        User user = refreshTokenEntity.getUser();

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                List.of(new SimpleGrantedAuthority(user.getUserType().toString()))
        );

        // 새로운 토큰 발급
        String newAccessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String newRefreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        // Refresh Token 교체 (Rotation)
        refreshTokenEntity.updateToken(newRefreshToken);

        // Refresh Token을 쿠키에 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60) // 7일
                .path("/")
                .build();

        // Access Token을 Authorization 헤더에 설정

        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return AccessTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    @Override
    public void saveRefreshToken(User user, String refreshToken) {
        // 기존 리프레시 토큰이 있다면 업데이트, 없다면 새로 생성
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUser(user)
                .orElse(RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .build());

        refreshTokenEntity.updateToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public void logout(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN));

        refreshTokenRepository.delete(refreshTokenEntity);
    }

    /**
     * 로그인 성공 시, 엑세스 토큰과 리프레쉬 토큰을 발급하고 헤더에 넣는 코드를 공통으로 묶음.
     */
    @Override
    public String processLoginSuccess(User user, HttpServletResponse response) {
        // Access Token, Refresh Token 발급
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                List.of(new SimpleGrantedAuthority(user.getUserType().toString()))
        );

        String newAccessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String newRefreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        // Refresh Token 저장
        saveRefreshToken(user, newRefreshToken);

        // Refresh Token을 쿠키에 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60) // 7일(7 * 24 * 60 * 60)
                .path("/")
                .build();

        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return newAccessToken;
    }
}
