package com.laicos.khufarm.domain.wishList.converter;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import org.springframework.stereotype.Component;

@Component
public class WishListConverter {

    public static WishList toWishList(User user, Fruit fruit) {
        return WishList.builder()
                .user(user)
                .fruit(fruit)
                .build();
    }
}
