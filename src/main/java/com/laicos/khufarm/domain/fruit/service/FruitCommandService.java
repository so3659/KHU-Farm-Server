package com.laicos.khufarm.domain.fruit.service;

import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface FruitCommandService {

    void addFruit(User user, FruitAddRequest fruitAddRequest);
    void deleteFruit(User user, Long fruitId);
}
