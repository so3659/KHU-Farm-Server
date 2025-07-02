package com.laicos.khufarm.domain.wishList.repository;

import com.laicos.khufarm.domain.wishList.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long>, CustomWishListRepository{
}
