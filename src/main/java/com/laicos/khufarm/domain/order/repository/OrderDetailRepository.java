package com.laicos.khufarm.domain.order.repository;

import com.laicos.khufarm.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findOrderDetailById(Long id);
}
