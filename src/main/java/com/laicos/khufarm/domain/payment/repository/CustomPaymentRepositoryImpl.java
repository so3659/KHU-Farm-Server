package com.laicos.khufarm.domain.payment.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryStatus;
import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithOrder;
import com.laicos.khufarm.domain.openfeign.client.DeliveryInfoConfirm;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.payment.entity.Payment;
import com.laicos.khufarm.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.payment.entity.QPayment.payment;

@Repository
@RequiredArgsConstructor
public class CustomPaymentRepositoryImpl implements CustomPaymentRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final DeliveryInfoConfirm deliveryInfoConfirm;

    @Override
    public Slice<FruitResponseWithOrder> getPaidFruit(User user, Long cursorId, Pageable pageable){

        ObjectMapper objectMapper = new ObjectMapper();

        List<Payment> paymentList = jpaQueryFactory.selectFrom(payment)
                .leftJoin(payment.order).fetchJoin()
                .leftJoin(payment.user).fetchJoin()
                .where(
                        eqUserId(user.getId()), // 사용자 ID 조건
                        gtCursorId(cursorId) // 커서 조건
                )
                .orderBy(payment.id.desc())
                .limit(pageable.getPageSize() + 1) // 페이지 크기 + 1로 커서 기반 페이징 처리
                .fetch();

        List<Order> orderList = paymentList.stream()
                .map(Payment::getOrder)
                .toList();

        List<OrderDetail> orderDetailList = orderList.stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .toList();

        List<DeliveryStatus> deliveryStatusList = orderDetailList.stream()
                .map(orderDetail -> {
                    if (orderDetail.getDeliveryCompany() == null || orderDetail.getDeliveryNumber() == null) {
                        return null; // 배송 정보가 없는 경우는 null로 처리
                    }
                    // 배송 정보 확인 요청
                    ResponseEntity<String> response = deliveryInfoConfirm.confirmDeliveryInfo(orderDetail.getDeliveryCompany().getId(), orderDetail.getDeliveryNumber());
                    try {
                        return objectMapper.readValue(response.getBody(), DeliveryStatus.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        List<FruitResponseWithOrder> content = FruitConverter.toFruitDTOListWithOrder(orderDetailList, deliveryStatusList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqUserId(Long userId) {
        return (userId == null) ? null : payment.user.id.eq(userId);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : payment.id.gt(cursorId);
    }

}


