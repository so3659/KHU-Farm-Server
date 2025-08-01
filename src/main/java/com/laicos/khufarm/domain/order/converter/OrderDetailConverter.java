package com.laicos.khufarm.domain.order.converter;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.dto.response.OrderResponseWithDetail;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDetailConverter {

    public static OrderDetail toOrderDetail(Fruit fruit, Order order, int count) {
        return OrderDetail.builder()
                .price(fruit.getPrice())
                .count(count)
                .weight(fruit.getWeight())
                .isReviewed(false)
                .order(order)
                .fruit(fruit)
                .build();
    }

    public static OrderDetail toOrderDetailWithCart(Cart cart, Order order) {
        return OrderDetail.builder()
                .price(cart.getFruit().getPrice())
                .count(cart.getCount())
                .weight(cart.getFruit().getWeight())
                .isReviewed(false)
                .order(order)
                .fruit(cart.getFruit())
                .build();
    }

    public static OrderResponseWithDetail toOrderResponseWithDetail(OrderDetail orderDetail) {
        return OrderResponseWithDetail.builder()
                .orderId(orderDetail.getOrder().getId())
                .orderDetailId(orderDetail.getId())
                .merchantUid(orderDetail.getOrder().getMerchantUid())
                .ordererName(orderDetail.getOrder().getOrdererName())
                .totalPrice(orderDetail.getOrder().getTotalPrice())
                .fruitTitle(orderDetail.getFruit().getTitle())
                .orderCount(orderDetail.getCount())
                .portCode(orderDetail.getOrder().getPortCode())
                .address(orderDetail.getOrder().getAddress())
                .detailAddress(orderDetail.getOrder().getDetailAddress())
                .recipient(orderDetail.getOrder().getRecipient())
                .phoneNumber(orderDetail.getOrder().getPhoneNumber())
                .deliveryCompany(orderDetail.getDeliveryCompany() != null ? orderDetail.getDeliveryCompany().getName() : null)
                .deliveryNumber(orderDetail.getDeliveryNumber())
                .orderRequest(orderDetail.getOrder().getOrderRequest())
                .deliveryStatus(orderDetail.getOrderStatus().getDescription())
                .refundReason(orderDetail.getRefundReason() != null ? orderDetail.getRefundReason() : null)
                .createdAt(orderDetail.getOrder().getCreatedAt())
                .build();
    }

    public static List<OrderResponseWithDetail> toOrderResponseWithDetailList(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailConverter::toOrderResponseWithDetail)
                .collect(Collectors.toList());
    }

    public static OrderResponse toOrderResponse(OrderDetail orderDetail) {
        return OrderResponse.builder()
                .merchantUid(orderDetail.getOrder().getMerchantUid())
                .ordererName(orderDetail.getOrder().getOrdererName())
                .totalPrice(orderDetail.getOrder().getTotalPrice())
                .orderCount(orderDetail.getOrder().getOrderCount())
                .portCode(orderDetail.getOrder().getPortCode())
                .address(orderDetail.getOrder().getAddress())
                .detailAddress(orderDetail.getOrder().getDetailAddress())
                .recipient(orderDetail.getOrder().getRecipient())
                .phoneNumber(orderDetail.getOrder().getPhoneNumber())
                .orderRequest(orderDetail.getOrder().getOrderRequest())
                .build();
    }
}
