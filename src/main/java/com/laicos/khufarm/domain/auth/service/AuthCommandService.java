package com.laicos.khufarm.domain.auth.service;

import com.laicos.khufarm.domain.auth.dto.AccessTokenResponse;
import com.laicos.khufarm.domain.auth.dto.IDFindRequest;
import com.laicos.khufarm.domain.auth.dto.PasswordChangeRequest;
import com.laicos.khufarm.domain.auth.dto.PasswordFindRequest;
import com.laicos.khufarm.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthCommandService {

    AccessTokenResponse reissueToken(String oldRefreshToken, HttpServletResponse response);

    void saveRefreshToken(User user, String refreshToken);

    void logout(String refreshToken);

    String processLoginSuccess(User user, HttpServletResponse response);

    void sendLoginAuthMessage(PasswordFindRequest passwordFindRequest) throws Exception;

    String findId(IDFindRequest idFindRequest) throws Exception;

    void changePassword(User user, PasswordChangeRequest passwordChangeRequest) throws Exception;
}
