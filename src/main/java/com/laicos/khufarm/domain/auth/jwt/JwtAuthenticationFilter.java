package com.laicos.khufarm.domain.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.service.UserQueryService;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.AuthErrorStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final UserQueryService userQueryService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. Request Header에서 JWT 토큰 추출
            String token = resolveToken((HttpServletRequest) request);

            // 2. validateToken으로 토큰 유효성 검사
            if (token != null) {

                String userId = jwtTokenProvider.validateTokenAndGetSubject(token);
                User user = userQueryService.findUserById(Long.parseLong(userId));

                CustomUserDetails customUserDetails = new CustomUserDetails(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        customUserDetails,
                        null,
                        List.of(new SimpleGrantedAuthority(user.getUserType().toString()))
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (RestApiException e){
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, e);
        } catch (Exception e) {
            log.info("Invalid Access Token", e);
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, new RestApiException(AuthErrorStatus.INVALID_ACCESS_TOKEN));
        }
    }

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, RestApiException exception) throws IOException {
        response.setStatus(exception.getErrorCode().getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = Map.of(
                "timestamp", java.time.LocalDateTime.now().toString(),
                "code", exception.getErrorCode().getCode(),
                "message", exception.getErrorCode().getMessage()
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
