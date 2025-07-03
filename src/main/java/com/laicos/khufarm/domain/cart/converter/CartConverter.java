package com.laicos.khufarm.domain.cart.converter;

import com.laicos.khufarm.domain.cart.dto.response.CartResponse;
import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {

    public static Cart toCart(User user, Fruit fruit, Integer count) {
        return Cart.builder()
                .user(user)
                .fruit(fruit)
                .count(count)
                .build();
    }

    public static CartResponse toDTOList(User user, Slice<FruitResponseWithCount> fruitList){

        return CartResponse.builder()
                .userId(user.getId())
                .fruitsWithCount(fruitList)
                .build();
    }
}
