package com.laicos.khufarm.domain.inquiry.service;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.inquiry.converter.InquiryConverter;
import com.laicos.khufarm.domain.inquiry.converter.InquiryReplyConverter;
import com.laicos.khufarm.domain.inquiry.dto.InquiryReadCondition;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryReplyResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponseWithFruit;
import com.laicos.khufarm.domain.inquiry.dto.response.MyInquiryResponse;
import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import com.laicos.khufarm.domain.inquiry.repository.InquiryRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.InquiryErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryQueryServiceImpl implements InquiryQueryService {

    private final InquiryRepository inquiryRepository;

    @Override
    public Slice<InquiryResponse> getAllInquiry(Long cursorId, InquiryReadCondition inquiryReadCondition, Pageable pageable){

        return inquiryRepository.getAllInquiry(cursorId,inquiryReadCondition, pageable);
    }

    @Override
    public MyInquiryResponse getMyInquiry(Long inquiryId){
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RestApiException(InquiryErrorStatus.INQUIRY_NOT_FOUND));

        InquiryReplyResponse inquiryReplyResponse = inquiry.getInquiryReply() != null
                ? InquiryReplyConverter.toInquiryReplyDTO(inquiry.getInquiryReply())
                : null;

        InquiryResponse inquiryResponse = InquiryConverter.toInquiryDTO(inquiry, inquiryReplyResponse);

        FruitResponse fruitResponse = FruitConverter.toFruitDTO(inquiry.getFruit());

        return InquiryConverter.toMyInquiryDTO(fruitResponse, inquiryResponse);
    }

    @Override
    public Slice<InquiryResponseWithFruit> getSellerInquiry(Long cursorId, User user, Pageable pageable, InquiryReadCondition inquiryReadCondition){
        return  inquiryRepository.getSellerInquiry(cursorId, user, pageable, inquiryReadCondition);
    }
}
