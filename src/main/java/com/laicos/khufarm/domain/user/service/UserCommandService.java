package com.laicos.khufarm.domain.user.service;

import com.laicos.khufarm.domain.user.dto.request.UserJoinRequest;
import com.laicos.khufarm.domain.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface UserCommandService {

    UserResponse joinUser(Integer userType, UserJoinRequest userJoinRequest, HttpServletResponse response);
}
