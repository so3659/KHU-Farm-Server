package com.laicos.khufarm.domain.cart.converter;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
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
}
