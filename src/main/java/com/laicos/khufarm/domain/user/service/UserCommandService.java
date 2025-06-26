package com.laicos.khufarm.domain.user.service;

import com.laicos.khufarm.domain.user.dto.request.BusinessUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.FarmerUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.IndividualUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.UserLoginRequest;
import com.laicos.khufarm.domain.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface UserCommandService {

    UserResponse joinIndividualUser(IndividualUserJoinRequest individualUserJoinRequest, HttpServletResponse response);
    UserResponse joinBusinessUser(BusinessUserJoinRequest businessUserJoinRequest, HttpServletResponse response);
    UserResponse joinFarmerUser(FarmerUserJoinRequest farmerUserJoinRequest, HttpServletResponse response);
    UserResponse loginUser(UserLoginRequest userLoginRequest, HttpServletResponse response);
}
