package com.laicos.khufarm.domain.inquiry.dto;

import com.laicos.khufarm.domain.user.entity.User;
import lombok.Getter;

@Getter
public class InquiryReadCondition {

    private User user;
    private Long fruitId;

    public InquiryReadCondition(User user){
        this.user = user;
    }

    public InquiryReadCondition(Long fruitId) {
        this.fruitId = fruitId;
    }
}
