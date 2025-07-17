package com.laicos.khufarm.domain.inquiry.service;

import com.laicos.khufarm.domain.inquiry.dto.request.InquiryReplyRequest;
import com.laicos.khufarm.domain.inquiry.dto.request.InquiryRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface InquiryCommandService {

    void addInquiry(User user, InquiryRequest inquiryRequest, Long fruitId);

    void addInquiryReply(User user, InquiryReplyRequest inquiryReplyRequest, Long inquiryId);
}
