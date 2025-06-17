package com.laicos.khufarm.domain.fruit.service;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FruitQueryService {

    Slice<FruitResponse> getFruitList(Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable);
}
