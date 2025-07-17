package com.laicos.khufarm.domain.inquiry.converter;

import com.laicos.khufarm.domain.inquiry.dto.response.InquiryReplyResponse;
import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import com.laicos.khufarm.domain.inquiry.entity.InquiryReply;
import org.springframework.stereotype.Component;

@Component
public class InquiryReplyConverter {

    public static InquiryReply toInquiryReply(String content, Inquiry inquiry) {
        return InquiryReply.builder()
                .content(content)
                .inquiry(inquiry)
                .build();
    }

    public static InquiryReplyResponse toInquiryReplyDTO(InquiryReply inquiryReply) {
        return InquiryReplyResponse.builder()
                .content(inquiryReply.getContent())
                .sellerName(inquiryReply.getInquiry().getSeller().getBrandName())
                .createdAt(inquiryReply.getCreatedAt())
                .build();
    }
}
