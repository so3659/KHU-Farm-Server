package com.laicos.khufarm.domain.wishList.converter;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithWishListId;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.dto.response.WishListResponse;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class WishListConverter {

    public static WishList toWishList(User user, Fruit fruit) {
        return WishList.builder()
                .user(user)
                .fruit(fruit)
                .build();
    }

    public static WishListResponse toDTOList(User user, Slice<FruitResponseWithWishListId> fruitList){

        return WishListResponse.builder()
                .userId(user.getId())
                .fruitWithWishList(fruitList)
                .build();
    }
}
