package com.laicos.khufarm.domain.delivery.service;

import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.order.repository.OrderDetailRepository;
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
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void updateDeliveryStatus(User user, Long orderDetailId, DeliveryInfoRequest deliveryInfoRequest){

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_DETAIL_NOT_FOUND));


        orderDetail.updateDeliveryInfo(DeliveryCompany.fromName(deliveryInfoRequest.getDeliveryCompany()), deliveryInfoRequest.getDeliveryNumber());
        orderDetail.updateOrderStatus(OrderStatus.SHIPPING);
    }
}
