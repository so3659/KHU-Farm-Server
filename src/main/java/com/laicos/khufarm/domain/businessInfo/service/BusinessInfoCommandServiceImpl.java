package com.laicos.khufarm.domain.businessInfo.service;

import com.laicos.khufarm.domain.businessInfo.converter.BusinessInfoConverter;
import com.laicos.khufarm.domain.businessInfo.dto.BusinessInfoDto;
import com.laicos.khufarm.domain.businessInfo.entity.BusinessInfo;
import com.laicos.khufarm.domain.businessInfo.repository.BusinessInfoRepository;
import com.laicos.khufarm.domain.openfeign.client.BusinessInfoConfirm;
import com.laicos.khufarm.domain.openfeign.dto.reponse.BusinessInfoResponse;
import com.laicos.khufarm.domain.openfeign.dto.request.BusinessInfoRequest;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.BusinessInfoErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class BusinessInfoCommandServiceImpl implements BusinessInfoCommandService{

    private final BusinessInfoRepository businessInfoRepository;
    private final BusinessInfoConfirm businessInfoConfirm;

    @Override
    public void validateBusinessInfo(BusinessInfoDto businessInfoDto){

        BusinessInfoRequest request = BusinessInfoRequest.builder()
                .businesses(List.of(
                        BusinessInfoRequest.Business.builder()
                                .bNo(businessInfoDto.getBusinessId())
                                .startDt(businessInfoDto.getOpenDate())
                                .pNm(businessInfoDto.getBusinessName())
                                .build()
                ))
                .build();

        BusinessInfoResponse businessInfoResponse = businessInfoConfirm.confirmBusinessInfo(request);

        // 유효성 검사 결과가 유효하지 않은 경우 예외 처리
        if(businessInfoResponse.getData().getFirst().getValid().equals("02")){
            throw new RestApiException(BusinessInfoErrorStatus.INVALID_BUSINESS_INFO);
        }
    }

    @Override
    public void saveBusinessInfo(BusinessInfoDto businessInfoDto, User user){

        Boolean isExist = businessInfoRepository.existsByBusinessId(businessInfoDto.getBusinessId());

        if(isExist) {
            throw new RestApiException(BusinessInfoErrorStatus.BUSINESS_ALREADY_EXISTS);
        }

        // BusinessInfo 엔티티로 변환
        BusinessInfo businessInfo = BusinessInfoConverter.toBusinessInfo(businessInfoDto, user);

        // 비즈니스 정보 저장
        businessInfoRepository.save(businessInfo);
    }
}
