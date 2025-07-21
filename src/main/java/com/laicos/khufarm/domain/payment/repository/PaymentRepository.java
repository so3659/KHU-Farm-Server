package com.laicos.khufarm.domain.payment.repository;

import com.laicos.khufarm.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, CustomPaymentRepository{
}
