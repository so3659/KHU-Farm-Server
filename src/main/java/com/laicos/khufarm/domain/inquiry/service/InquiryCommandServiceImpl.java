package com.laicos.khufarm.domain.inquiry.service;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.inquiry.converter.InquiryConverter;
import com.laicos.khufarm.domain.inquiry.converter.InquiryReplyConverter;
import com.laicos.khufarm.domain.inquiry.dto.request.InquiryReplyRequest;
import com.laicos.khufarm.domain.inquiry.dto.request.InquiryRequest;
import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import com.laicos.khufarm.domain.inquiry.repository.InquiryRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.InquiryErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryCommandServiceImpl implements InquiryCommandService{

    private final InquiryRepository inquiryRepository;
    private final FruitRepository fruitRepository;

    @Override
    public void addInquiry(User user, InquiryRequest inquiryRequest, Long fruitId){

        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        Inquiry inquiry = InquiryConverter.toInquiry(user, inquiryRequest, fruit);

        inquiryRepository.save(inquiry);
    }

    public void addInquiryReply(User user, InquiryReplyRequest inquiryReplyRequest, Long inquiryId){
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RestApiException(InquiryErrorStatus.INQUIRY_NOT_FOUND));

        inquiry.addInquiryReply(InquiryReplyConverter.toInquiryReply(inquiryReplyRequest.getContent(), inquiry));
        inquiryRepository.save(inquiry);
    }
}
