package com.laicos.khufarm.domain.businessInfo.converter;

import com.laicos.khufarm.domain.businessInfo.dto.BusinessInfoDto;
import com.laicos.khufarm.domain.businessInfo.entity.BusinessInfo;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BusinessInfoConverter {

    public static BusinessInfo toBusinessInfo(BusinessInfoDto businessInfoDto, User user){
        return BusinessInfo.builder()
                .businessId(businessInfoDto.getBusinessId())
                .businessName(businessInfoDto.getBusinessName())
                .businessRegistrationDate(businessInfoDto.getOpenDate())
                .user(user)
                .build();
    }
}
