package com.laicos.khufarm.domain.review.dto;

import com.laicos.khufarm.domain.user.entity.User;
import lombok.Getter;

@Getter
public class ReviewReadCondition {

    private User user;
    private Long fruitId;

    public ReviewReadCondition(User user){
        this.user = user;
    }

    public ReviewReadCondition(Long fruitId) {
        this.fruitId = fruitId;
    }
}
