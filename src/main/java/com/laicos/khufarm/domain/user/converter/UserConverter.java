package com.laicos.khufarm.domain.user.converter;

import com.laicos.khufarm.domain.user.dto.request.UserJoinRequest;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.enums.UserStatus;
import com.laicos.khufarm.domain.user.enums.UserType;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.MemberErrorStatus;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    public static User toUser(Integer userTypeId, UserJoinRequest userJoinRequest) {

        UserType userType = switch (userTypeId) {
            case 1 -> UserType.ROLE_INDIVIDUAL;
            case 2 -> UserType.ROLE_BUSINESS;
            case 3 -> UserType.ROLE_ADMIN;
            default -> throw new RestApiException(MemberErrorStatus.USERTYPE_NOT_FOUND);
        };

        return User.builder()
                .name(userJoinRequest.getName())
                .email(userJoinRequest.getEmail())
                .phoneNumber(userJoinRequest.getPhoneNumber())
                .userId(userJoinRequest.getUserId())
                .userStatus(UserStatus.ACTIVE)
                .version(userJoinRequest.getVersion())
                .userType(userType)
                .build();
    }
}
