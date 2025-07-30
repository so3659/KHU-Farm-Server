package com.laicos.khufarm.domain.inquiry.converter;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.inquiry.dto.request.InquiryRequest;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryReplyResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponseWithFruit;
import com.laicos.khufarm.domain.inquiry.dto.response.MyInquiryResponse;
import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class InquiryConverter {

    public static Inquiry toInquiry(User user, InquiryRequest inquiryRequest, Fruit fruit) {
        return Inquiry.builder()
                .isPrivate(inquiryRequest.isPrivate())
                .content(inquiryRequest.getContent())
                .isAnswered(false)
                .seller(fruit.getSeller())
                .fruit(fruit)
                .user(user)
                .build();
    }

    public static InquiryResponse toInquiryDTO(Inquiry inquiry, InquiryReplyResponse reply) {
        return InquiryResponse.builder()
                .inquiryId(inquiry.getId())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .isPrivate(inquiry.isPrivate())
                .reply(reply)
                .build();
    }

    public static List<InquiryResponse> toInquiryDTOList(List<Inquiry> inquiries, List<InquiryReplyResponse> replyList) {
        return IntStream.range(0, inquiries.size())
                .mapToObj(i -> toInquiryDTO(inquiries.get(i), replyList.get(i)))
                .toList();
    }

    public static InquiryResponseWithFruit toInquiryWithFruitDTO(Inquiry inquiry, InquiryReplyResponse reply, FruitResponse fruitResponse) {
        return InquiryResponseWithFruit.builder()
                .fruitResponse(fruitResponse)
                .inquiryId(inquiry.getId())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .isPrivate(inquiry.isPrivate())
                .reply(reply)
                .build();
    }

    public static List<InquiryResponseWithFruit> toInquiryWithFruitDTOList(List<Inquiry> inquiries, List<InquiryReplyResponse> replyList, List<FruitResponse> fruitResponses) {
        return IntStream.range(0, inquiries.size())
                .mapToObj(i -> toInquiryWithFruitDTO(inquiries.get(i), replyList.get(i), fruitResponses.get(i)))
                .toList();
    }

    public static MyInquiryResponse toMyInquiryDTO(FruitResponse fruitResponse, InquiryResponse inquiryResponse) {
        return MyInquiryResponse.builder()
                .fruitResponse(fruitResponse)
                .inquiryResponse(inquiryResponse)
                .build();
    }
}
