package com.laicos.khufarm.domain.wishList.service;

import com.laicos.khufarm.domain.user.entity.User;

public interface WishListCommandService {

    void addWishList(User user, Long fruitId);
    void deleteWishList(User user, Long wishListId);
}
