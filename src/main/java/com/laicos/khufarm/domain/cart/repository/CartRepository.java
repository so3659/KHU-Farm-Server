package com.laicos.khufarm.domain.cart.repository;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, CustomCartRepository{

    Optional<Cart> findByUserAndId(User user, Long id);
}
