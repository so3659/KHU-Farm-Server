package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseIsWish;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomFruitRepository {
    Slice<FruitResponseIsWish> getFruitByConditions(User user, Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable);
    Slice<FruitResponseWithCount> getFruitBySeller(User user, Long cursorId, Pageable pageable);
}
