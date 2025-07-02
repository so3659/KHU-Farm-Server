package com.laicos.khufarm.domain.wishList.service;

import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.dto.response.WishListResponse;
import org.springframework.data.domain.Pageable;

public interface WishListQueryService {

    WishListResponse getWishList(Long cursorId, User user, Pageable pageable);
}
