package com.laicos.khufarm.domain.order.converter;

import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.order.dto.request.ShippingInfo;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.dto.response.PrePareOrderResponse;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderConverter {

    public static Order toOrder(ShippingInfo shippingInfo, int totalPrice ,String merchantUid, int totalCount, User user) {
        return Order.builder()
                .merchantUid(merchantUid)
                .ordererName(user.getName())
                .totalPrice(totalPrice)
                .orderCount(totalCount)
                .portCode(shippingInfo.getPortCode())
                .address(shippingInfo.getAddress())
                .detailAddress(shippingInfo.getDetailAddress())
                .recipient(shippingInfo.getRecipient())
                .phoneNumber(shippingInfo.getPhoneNumber())
                .orderRequest(shippingInfo.getOrderRequest())
                .orderStatus(OrderStatus.PAYMENT_STANDBY)
                .user(user)
                .build();
    }

    public static OrderResponse toOrderResponse(Order order){
        return OrderResponse.builder()
                .merchantUid(order.getMerchantUid())
                .ordererName(order.getOrdererName())
                .totalPrice(order.getTotalPrice())
                .orderCount(order.getOrderCount())
                .portCode(order.getPortCode())
                .address(order.getAddress())
                .detailAddress(order.getDetailAddress())
                .recipient(order.getRecipient())
                .phoneNumber(order.getPhoneNumber())
                .orderRequest(order.getOrderRequest())
                .build();
    }

    public static PrePareOrderResponse toPrePareOrderResponse(AddressResponse addressResponse, List<FruitResponseWithCount> fruitResponseWithCount) {
        return PrePareOrderResponse.builder()
                .addressResponse(addressResponse)
                .fruitResponseWithCount(fruitResponseWithCount)
                .build();
    }
}
