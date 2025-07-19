package com.laicos.khufarm.domain.fruit.service;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseIsWish;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import com.laicos.khufarm.domain.wishList.repository.WishListRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FruitQueryServiceImpl implements FruitQueryService{

    private final FruitRepository fruitRepository;
    private final WishListRepository wishListRepository;

    @Override
    public Slice<FruitResponseIsWish> getFruitList(User user, Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable) {

        return fruitRepository.getFruitByConditions(user, cursorId, fruitReadCondition, pageable);
    }

    @Override
    public FruitResponseIsWish getFruit(User user, Long fruitId){
        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        WishList wishList = wishListRepository.findByUserAndFruit(user, fruit)
                .orElse(null);

        return FruitConverter.toFruitIsWishDTO(fruit, wishList);
    }
}
