package com.laicos.khufarm.domain.cart.service;

import com.laicos.khufarm.domain.cart.converter.CartConverter;
import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.cart.repository.CartRepository;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.CartListErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartCommandServiceImpl implements CartCommandService{

    private final FruitRepository fruitRepository;
    private final CartRepository cartRepository;

    @Override
    public void addCart(User user, Long fruitId, Integer count){

        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        Cart cart = CartConverter.toCart(user, fruit, count);

        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(User user, Long cartId){

        Cart cart = cartRepository.findByUserAndId(user, cartId)
                .orElseThrow(() -> new RestApiException(CartListErrorStatus.CART_NOT_FOUND));

        cartRepository.delete(cart);
    }

    @Override
    public void increaseCartCount(User user, Long cartId){
        Cart cart = cartRepository.findByUserAndId(user, cartId)
                .orElseThrow(() -> new RestApiException(CartListErrorStatus.CART_NOT_FOUND));

        cart.increaseCount();
    }

    @Override
    public void decreaseCartCount(User user, Long cartId){
        Cart cart = cartRepository.findByUserAndId(user, cartId)
                .orElseThrow(() -> new RestApiException(CartListErrorStatus.CART_NOT_FOUND));

        cart.decreaseCount();
    }
}
