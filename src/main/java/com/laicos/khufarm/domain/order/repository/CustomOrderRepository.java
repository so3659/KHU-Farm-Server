package com.laicos.khufarm.domain.order.repository;

import com.laicos.khufarm.domain.order.dto.response.OrderResponseWithDetail;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomOrderRepository {
    Slice<OrderResponseWithDetail> getOrderBySeller(User user, Long cursorId, Pageable pageable);
}
