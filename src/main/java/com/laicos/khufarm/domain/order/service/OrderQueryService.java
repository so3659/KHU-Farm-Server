package com.laicos.khufarm.domain.order.service;

import com.laicos.khufarm.domain.order.dto.response.PrePareOrderResponse;
import com.laicos.khufarm.domain.user.entity.User;

import java.util.List;

public interface OrderQueryService {

    PrePareOrderResponse getPrepareCartOrder(User user, List<Long> cartIds);
    PrePareOrderResponse getPrepareDirectOrder(User user, Long fruitId, Integer orderCount);
}
