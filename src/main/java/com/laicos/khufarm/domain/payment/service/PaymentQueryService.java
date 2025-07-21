package com.laicos.khufarm.domain.payment.service;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithOrder;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PaymentQueryService {

    Slice<FruitResponseWithOrder> getPaidFruit(User user, Long cursorId, Pageable pageable);
}
