package com.laicos.khufarm.domain.payment.handler;

import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.order.repository.OrderRepository;
import com.laicos.khufarm.domain.payment.entity.Payment;
import com.laicos.khufarm.domain.payment.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentFailureHandler {

    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleOrderFailure(Order order, String failReason) {
        order.updateOrderStatus(OrderStatus.ORDER_FAILED);
        order.updateFailReason(failReason);
        orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePaymentFailure(Order order, Payment payment, String failReason, PaymentStatus paymentStatus) {
        order.updateOrderStatus(OrderStatus.ORDER_FAILED);
        order.updateFailReason(failReason);

        // 이 주문에 이미 연결된 Payment 정보가 있는지 확인
        Payment paymentToProcess = order.getPayment();

        if (paymentToProcess != null) {
            paymentToProcess.updatePaymentStatus(paymentStatus);
            paymentToProcess.updateFailReason(failReason);
        } else {
            payment.updatePaymentStatus(paymentStatus);
            payment.updateFailReason(failReason);

            order.addPayment(payment);
        }
        orderRepository.save(order);
    }
}
