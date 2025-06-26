package com.laicos.khufarm.domain.user.converter;

import com.laicos.khufarm.domain.user.dto.request.BusinessUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.FarmerUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.IndividualUserJoinRequest;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.enums.UserStatus;
import com.laicos.khufarm.domain.user.enums.UserType;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    public static User toIndividualUser(IndividualUserJoinRequest individualUserJoinRequest) {

            return User.builder()
                    .name(individualUserJoinRequest.getName())
                    .email(individualUserJoinRequest.getEmail())
                    .phoneNumber(individualUserJoinRequest.getPhoneNumber())
                    .userId(individualUserJoinRequest.getUserId())
                    .userStatus(UserStatus.ACTIVE)
                    .version(individualUserJoinRequest.getVersion())
                    .userType(UserType.ROLE_INDIVIDUAL)
                    .build();
    }

    public static User toBusinessUser(BusinessUserJoinRequest businessUserJoinRequest) {

            return User.builder()
                    .name(businessUserJoinRequest.getName())
                    .email(businessUserJoinRequest.getEmail())
                    .phoneNumber(businessUserJoinRequest.getPhoneNumber())
                    .userId(businessUserJoinRequest.getUserId())
                    .userStatus(UserStatus.ACTIVE)
                    .version(businessUserJoinRequest.getVersion())
                    .userType(UserType.ROLE_BUSINESS)
                    .build();
    }

    public static User toFarmerUser(FarmerUserJoinRequest farmerUserJoinRequest) {

            return User.builder()
                    .name(farmerUserJoinRequest.getName())
                    .email(farmerUserJoinRequest.getEmail())
                    .phoneNumber(farmerUserJoinRequest.getPhoneNumber())
                    .userId(farmerUserJoinRequest.getUserId())
                    .userStatus(UserStatus.STAND_BY)
                    .version(farmerUserJoinRequest.getVersion())
                    .userType(UserType.ROLE_FARMER)
                    .build();
    }
}
