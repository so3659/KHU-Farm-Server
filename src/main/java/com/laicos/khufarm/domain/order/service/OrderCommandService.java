package com.laicos.khufarm.domain.order.service;

import com.laicos.khufarm.domain.order.dto.request.OrderRequest;
import com.laicos.khufarm.domain.order.dto.request.RefundRequest;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.user.entity.User;

public interface OrderCommandService {

    OrderResponse orderByCart(User user, OrderRequest.CartOrderRequest request);
    OrderResponse orderByDirect(User user, OrderRequest.DirectOrderRequest request);
    void refundOrder(User user, Long orderDetailId, RefundRequest request);
}
