package com.laicos.khufarm.domain.payment.service;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.cart.repository.CartRepository;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.order.repository.OrderRepository;
import com.laicos.khufarm.domain.payment.converter.PaymentConverter;
import com.laicos.khufarm.domain.payment.dto.PortoneConfirmDto;
import com.laicos.khufarm.domain.payment.dto.PortoneWebhookDto;
import com.laicos.khufarm.domain.payment.enums.PaymentStatus;
import com.laicos.khufarm.domain.payment.handler.PaymentFailureHandler;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.CartListErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.PaymentErrorStatus;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PaymentCommandServiceImpl implements PaymentCommandService{

    private final OrderRepository orderRepository;
    private final FruitRepository fruitRepository;
    private final UserRepository  userRepository;
    private final CartRepository cartRepository;
    private final PaymentFailureHandler paymentFailureHandler;
    private final IamportClient iamportClient;

    @Override
    public void confirmPayment(PortoneConfirmDto portoneConfirmDto){

        Order order = orderRepository.findByMerchantUid(portoneConfirmDto.getMerchantUid())
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_NOT_FOUND));

        // 결제 금액 비교
        if(order.getTotalPrice() != portoneConfirmDto.getAmount().intValue()) {

            paymentFailureHandler.handleOrderFailure(order, OrderErrorStatus.PRICE_NOT_MATCH.getMessage());

            throw new RestApiException(OrderErrorStatus.PRICE_NOT_MATCH);
        }

        for(OrderDetail orderDetail : order.getOrderDetails()){
            // 주문정보에 있는 상품아이디로 상품이 존재하는지 검사
            Fruit fruit = fruitRepository.findById(orderDetail.getFruit().getId())
                    .orElseThrow(() -> {
                        paymentFailureHandler.handleOrderFailure(order, FruitErrorStatus.FRUIT_NOT_FOUND.getMessage());

                        return new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND);
                    });

            // 주문정보에 있는 상품아이디로 재고가 있는지 검사
            if (fruit.getStock() <= 0) {

                paymentFailureHandler.handleOrderFailure(order, OrderErrorStatus.OUT_OF_STOCK.getMessage());

                throw new RestApiException(OrderErrorStatus.OUT_OF_STOCK);
            }
        }

    }

    @Override
    public void webhookPayment(PortoneWebhookDto webhookDto) throws IamportResponseException, IOException {

        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(webhookDto.getImpUid());


            // webhookDto의 merchant_uid로 주문 정보를 조회합니다.
            Order order = orderRepository.findByMerchantUid(webhookDto.getMerchantUid())
                    .orElseThrow(() -> {
                        CancelData cancelData = cancelPayment(iamportResponse);

                        try {
                            iamportClient.cancelPaymentByImpUid(cancelData);
                        } catch (Exception e) {
                            log.error("Failed to cancel payment for merchant_uid: {}", webhookDto.getMerchantUid(), e);
                        }

                        return new RestApiException(OrderErrorStatus.ORDER_NOT_FOUND, HttpStatus.OK);
                    });

            com.laicos.khufarm.domain.payment.entity.Payment payment = PaymentConverter.toPayment(order, iamportResponse);

            // 이미 처리된 주문인지 상태를 확인
            if (order.getOrderStatus() == OrderStatus.ORDER_COMPLETED) {
                log.info("이미 처리된 주문입니다. merchant_uid: {}", webhookDto.getMerchantUid());
                return;
            }

            // 결제 완료가 아니면
            if(!iamportResponse.getResponse().getStatus().equals("paid")) {

                paymentFailureHandler.handlePaymentFailure(order, payment, PaymentErrorStatus.PAYMENT_FAILED.getMessage(), PaymentStatus.PAYMENT_FAILED);

                throw new RestApiException(PaymentErrorStatus.PAYMENT_FAILED, HttpStatus.OK);
            }

            // 결제 금액 비교
            if(order.getTotalPrice() != iamportResponse.getResponse().getAmount().intValue()) {

                paymentFailureHandler.handlePaymentFailure(order, payment, PaymentErrorStatus.PRICE_NOT_MATCH.getMessage(), PaymentStatus.PAYMENT_CANCELLED);

                CancelData cancelData = cancelPayment(iamportResponse);
                iamportClient.cancelPaymentByImpUid(cancelData);

                throw new RestApiException(PaymentErrorStatus.PRICE_NOT_MATCH, HttpStatus.OK);
            }

            // 재고 감소
            for(OrderDetail orderDetail : order.getOrderDetails()){
                Fruit fruit = fruitRepository.findById(orderDetail.getFruit().getId())
                        .orElseThrow(() -> {
                            paymentFailureHandler.handlePaymentFailure(order, payment, FruitErrorStatus.FRUIT_NOT_FOUND.getMessage(), PaymentStatus.PAYMENT_CANCELLED);

                            CancelData cancelData = cancelPayment(iamportResponse);

                            try {
                                iamportClient.cancelPaymentByImpUid(cancelData);
                            } catch (Exception e) {
                                log.error("Failed to cancel payment for merchant_uid: {}", webhookDto.getMerchantUid(), e);
                            }

                            return new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND, HttpStatus.OK);
                        });


                fruit.decreaseFruitStock(orderDetail.getCount());
            }

            order.addPayment(payment);
            order.updateOrderStatus(OrderStatus.ORDER_COMPLETED);

            // User 구매 금액 및 구매 무게 업데이트
            updateUserTotalWeight(order);
            updateUserTotalPrice(order);

    }

    @Override
    public void deleteCartList(User user, List<Long> cartIdList){
        if(!cartIdList.isEmpty()){
            List<Cart> cartList = cartIdList.stream()
                    .map(cartId -> cartRepository.findByUserAndId(user, cartId)
                            .orElseThrow(() -> new RestApiException(CartListErrorStatus.CART_NOT_FOUND)))
                    .toList();

            cartRepository.deleteAll(cartList);
        }
    }

    private CancelData cancelPayment(IamportResponse<Payment> response) {
        return new CancelData(response.getResponse().getImpUid(), true);
    }

    private void updateUserTotalWeight(Order order) {

        User user = userRepository.getUserById(order.getUser().getId());

        List<OrderDetail> orderDetailList = order.getOrderDetails();

        for(OrderDetail orderDetail : orderDetailList) {
            user.updateTotalWeight(orderDetail.getWeight()* orderDetail.getCount());
        }
    }

    private void updateUserTotalPrice(Order order) {

        User user = userRepository.getUserById(order.getUser().getId());

        List<OrderDetail> orderDetailList = order.getOrderDetails();

        for(OrderDetail orderDetail : orderDetailList) {
            user.updateTotalPrice(orderDetail.getPrice()* orderDetail.getCount());
        }
    }
}