package com.laicos.khufarm.domain.wishList.service;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.converter.WishListConverter;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import com.laicos.khufarm.domain.wishList.repository.WishListRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.WishListErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WishListCommandServiceImpl implements WishListCommandService{

    private final FruitRepository fruitRepository;
    private final WishListRepository wishListRepository;

    @Override
    public void addWishList(User user, Long fruitId){
        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        WishList wishList = WishListConverter.toWishList(user, fruit);

        wishListRepository.save(wishList);
    }

    @Override
    public void deleteWishList(User user, Long wishListId) {

        WishList wishList = wishListRepository.findByUserAndId(user, wishListId)
                .orElseThrow(() -> new RestApiException(WishListErrorStatus.WISHLIST_NOT_FOUND));

        wishListRepository.delete(wishList);
    }
}
