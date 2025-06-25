package com.laicos.khufarm.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laicos.khufarm.global.common.base.BaseResponse;
import com.laicos.khufarm.global.common.exception.code.status.GlobalErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

        // BaseResponse 형식으로 응답 생성
        BaseResponse<String> baseResponse = BaseResponse.onFailure("COMMON403", GlobalErrorStatus._FORBIDDEN.getMessage(), null);

        // JSON 응답 작성
        String jsonResponse = objectMapper.writeValueAsString(baseResponse);
        response.getWriter().write(jsonResponse);
    }
}