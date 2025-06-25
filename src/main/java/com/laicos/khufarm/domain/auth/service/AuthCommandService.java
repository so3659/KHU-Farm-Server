package com.laicos.khufarm.domain.auth.service;

import com.laicos.khufarm.domain.auth.dto.AccessTokenResponse;
import com.laicos.khufarm.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthCommandService {

    AccessTokenResponse reissueToken(String oldRefreshToken, HttpServletResponse response);

    void saveRefreshToken(User user, String refreshToken);

    void logout(String refreshToken);

    String processLoginSuccess(User user, HttpServletResponse response);
}
