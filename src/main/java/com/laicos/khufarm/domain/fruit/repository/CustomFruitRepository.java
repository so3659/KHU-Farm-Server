package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomFruitRepository {
    Slice<FruitResponse> getFruitByConditions(Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable);

}
