package com.laicos.khufarm.domain.order.repository;

import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

    Optional<Order> findByUserAndMerchantUid(User user, String merchantUid);
    Optional<Order> findByMerchantUid(String merchantUid);
    Optional<Order> findByUserAndId(User user, Long orderId);
}
