package com.laicos.khufarm.domain.businessInfo.service;

import com.laicos.khufarm.domain.businessInfo.dto.BusinessInfoDto;
import com.laicos.khufarm.domain.user.entity.User;

public interface BusinessInfoCommandService {

    void validateBusinessInfo(BusinessInfoDto businessInfoDto);
    void saveBusinessInfo(BusinessInfoDto businessInfoDto, User user);
}
