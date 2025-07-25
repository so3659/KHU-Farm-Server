package com.laicos.khufarm.domain.review.dto;

import com.laicos.khufarm.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewReadCondition {

    private User user;
    private Long fruitId;
    private boolean isAnswered;

    public ReviewReadCondition(User user){
        this.user = user;
    }

    public ReviewReadCondition(Long fruitId) {
        this.fruitId = fruitId;
    }

    public ReviewReadCondition(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }
}
