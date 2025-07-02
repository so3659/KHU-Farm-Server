package com.laicos.khufarm.domain.wishList.repository;

import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.dto.response.WishListResponse;
import org.springframework.data.domain.Pageable;

public interface CustomWishListRepository {

    WishListResponse getWishList(Long cursorId, User user, Pageable pageable);
}
