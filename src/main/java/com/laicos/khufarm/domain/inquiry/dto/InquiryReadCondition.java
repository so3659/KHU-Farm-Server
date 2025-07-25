package com.laicos.khufarm.domain.inquiry.dto;

import com.laicos.khufarm.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryReadCondition {

    private User user;
    private Long fruitId;
    private boolean isAnswered;

    public InquiryReadCondition(User user){
        this.user = user;
    }

    public InquiryReadCondition(Long fruitId) {
        this.fruitId = fruitId;
    }

    public InquiryReadCondition(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }
}
