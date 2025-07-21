package com.laicos.khufarm.domain.delivery.service;

import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.order.repository.OrderRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryCommandServiceImpl implements DeliveryCommandService{

    private final OrderRepository orderRepository;

    @Override
    public void updateDeliveryStatus(User user, Long orderId, DeliveryInfoRequest deliveryInfoRequest){
        Order order = orderRepository.findByUserAndId(user, orderId)
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_NOT_FOUND));

        order.updateDeliveryInfo(DeliveryCompany.fromName(deliveryInfoRequest.getDeliveryCompany()), deliveryInfoRequest.getDeliveryNumber());
        order.updateOrderStatus(OrderStatus.SHIPPING);
    }
}
