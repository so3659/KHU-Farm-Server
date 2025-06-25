package com.laicos.khufarm.domain.auth.jwt;

import com.laicos.khufarm.domain.auth.dto.JwtToken;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.AuthErrorStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(
            // secret key
            @Value("${jwt.secret}") final String secretKey,
            // access token 유효 시간
            @Value("${jwt.accessExpiration}") final long accessTokenValidityInMilliseconds,
            // refresh token 유효 시간
            @Value("${jwt.refreshExpiration}") final long refreshTokenValidityInMilliseconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    // User 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        // CustomUserDetails에서 사용자 ID 추출
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId().toString(); // 사용자 ID를 문자열로 변환

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        String accessToken = Jwts.builder()
                .subject(userId)
                .claim("auth", authorities)
                .expiration(accessTokenExpiresIn)
                .signWith(secretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .subject(userId)
                .expiration(new Date(now.getTime() + refreshTokenValidityInMilliseconds))
                .signWith(secretKey)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 정보를 검증하는 메서드
    public String validateTokenAndGetSubject(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new RestApiException(AuthErrorStatus.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new RestApiException(AuthErrorStatus.EXPIRED_MEMBER_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new RestApiException(AuthErrorStatus.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new RestApiException(AuthErrorStatus.EMPTY_JWT);
        } catch (Exception e) {
            log.info("Invalid Access Token", e);
            throw new RestApiException(AuthErrorStatus.INVALID_ACCESS_TOKEN);
        }
    }
}
