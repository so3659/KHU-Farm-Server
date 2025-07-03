package com.laicos.khufarm.domain.cart.service;

import com.laicos.khufarm.domain.user.entity.User;

public interface CartCommandService {

    void addCart(User user, Long fruitId, Integer count);
    void deleteCart(User user, Long cartId);
}
