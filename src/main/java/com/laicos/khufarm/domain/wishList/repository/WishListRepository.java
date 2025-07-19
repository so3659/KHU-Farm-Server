package com.laicos.khufarm.domain.wishList.repository;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long>, CustomWishListRepository{

    Optional<WishList> findByUserAndId(User user, Long id);

    Optional<WishList> findByUserAndFruit(User user, Fruit fruit);
}
