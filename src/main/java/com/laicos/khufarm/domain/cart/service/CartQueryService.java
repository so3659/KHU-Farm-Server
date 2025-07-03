package com.laicos.khufarm.domain.cart.service;

import com.laicos.khufarm.domain.cart.dto.response.CartResponse;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;

public interface CartQueryService {

    CartResponse getCart(Long cursorId, User user, Pageable pageable);
}
