package com.laicos.khufarm.domain.fruit.service;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseIsWish;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FruitQueryService {

    Slice<FruitResponseIsWish> getFruitList(User user, Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable);
}
