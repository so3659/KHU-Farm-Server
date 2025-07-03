package com.laicos.khufarm.domain.cart.service;

import com.laicos.khufarm.domain.cart.dto.response.CartResponse;
import com.laicos.khufarm.domain.cart.repository.CartRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartQueryServiceImpl implements CartQueryService{

    private final CartRepository cartRepository;

    @Override
    public CartResponse getCart(Long cursorId, User user, Pageable pageable) {
        return cartRepository.getCart(cursorId, user, pageable);
    }
}
