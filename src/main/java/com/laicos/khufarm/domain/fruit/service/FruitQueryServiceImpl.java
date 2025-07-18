package com.laicos.khufarm.domain.fruit.service;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseIsWish;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FruitQueryServiceImpl implements FruitQueryService{

    private final FruitRepository fruitRepository;

    @Override
    @Transactional(readOnly = true)
    public Slice<FruitResponseIsWish> getFruitList(User user, Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable) {

        return fruitRepository.getFruitByConditions(user, cursorId, fruitReadCondition, pageable);
    }
}
