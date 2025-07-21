package com.laicos.khufarm.domain.payment.repository;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithOrder;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomPaymentRepository {

    Slice<FruitResponseWithOrder> getPaidFruit(User user, Long cursorId, Pageable pageable);
}
