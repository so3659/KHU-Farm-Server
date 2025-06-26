package com.laicos.khufarm.domain.businessInfo.service;

import com.laicos.khufarm.domain.businessInfo.dto.BusinessInfoDto;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BusinessInfoCommandServiceImpl implements BusinessInfoCommandService{

    @Override
    public void validateBusinessInfo(BusinessInfoDto businessInfoDto){

    }

    @Override
    public void saveBusinessInfo(BusinessInfoDto businessInfoDto, User user){

    }
}
