package com.laicos.khufarm.domain.wishList.service;

import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.dto.response.WishListResponse;
import com.laicos.khufarm.domain.wishList.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WishListQueryServiceImpl implements WishListQueryService{

    private final WishListRepository wishListRepository;

    @Override
    public WishListResponse getWishList(Long cursorId, User user, Pageable pageable){

        return wishListRepository.getWishList(cursorId, user, pageable);
    }
}
