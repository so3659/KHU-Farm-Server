package com.laicos.khufarm.domain.order.converter;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import org.springframework.stereotype.Component;

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
}
