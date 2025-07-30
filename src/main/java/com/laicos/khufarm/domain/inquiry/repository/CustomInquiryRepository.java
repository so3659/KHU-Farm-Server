package com.laicos.khufarm.domain.inquiry.repository;

import com.laicos.khufarm.domain.inquiry.dto.InquiryReadCondition;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponseWithFruit;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomInquiryRepository {
    Slice<InquiryResponse> getAllInquiry(Long cursorId, InquiryReadCondition inquiryReadCondition, Pageable pageable);
    Slice<InquiryResponseWithFruit> getSellerInquiry(Long cursorId, User user, Pageable pageable, InquiryReadCondition inquiryReadCondition);
}
