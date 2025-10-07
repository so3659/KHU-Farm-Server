package com.laicos.khufarm.domain.order.repository;

import com.laicos.khufarm.domain.delivery.service.DeliveryQueryService;
import com.laicos.khufarm.domain.order.converter.OrderDetailConverter;
import com.laicos.khufarm.domain.order.dto.response.OrderResponseWithDetail;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.SellerErrorStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.order.entity.QOrderDetail.orderDetail;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final SellerRepository sellerRepository;
    private final DeliveryQueryService deliveryQueryService;

    @Override
    public Slice<OrderResponseWithDetail> getOrderBySeller(User user, Long cursorId, Pageable pageable, Long orderStatusId){

        Seller seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SellerErrorStatus.SELLER_NOT_FOUND));

        List<OrderDetail> orderDetailList = jpaQueryFactory.selectFrom(orderDetail)
                .leftJoin(orderDetail.order).fetchJoin()
                .leftJoin(orderDetail.fruit).fetchJoin()
                .leftJoin(orderDetail.review).fetchJoin()
                .where(
                        eqSellerId(seller.getId()), // 판매자 ID 조건
                        eqOrderStatus(orderStatusId), // 주문 상태 조건
                        ltCursorId(cursorId) // 커서 조건
                )
                .orderBy(orderDetail.id.desc())
                .limit(pageable.getPageSize() + 1) // 페이지 크기 + 1로 커서 기반 페이징 처리
                .fetch();

        List<OrderResponseWithDetail> content = OrderDetailConverter.toOrderResponseWithDetailList(orderDetailList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqSellerId(Long sellerId) {
        return (sellerId == null) ? null : orderDetail.fruit.seller.id.eq(sellerId);
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        return (cursorId == null) ? null : orderDetail.id.lt(cursorId);
    }

    private BooleanExpression eqOrderStatus(Long orderStatusId) {
        if (orderStatusId == null) {
            return null; // 상태가 지정되지 않으면 null 반환
        }

        // switch 문을 사용하여 로직을 더 명확하게 처리
        return switch (orderStatusId.intValue()) {
            case 1 ->
                //배송 준비중(PREPARING_SHIPMENT)
                    orderDetail.orderStatus.eq(OrderStatus.PREPARING_SHIPMENT);
            case 2 ->
                //배송중(SHIPPING), 배달 완료(SHIPMENT_COMPLETED)
                    orderDetail.orderStatus.in(OrderStatus.SHIPPING, OrderStatus.SHIPMENT_COMPLETED);
            case 3 ->
                //환불 대기(REFUND_REQUESTED)
                    orderDetail.orderStatus.eq(OrderStatus.REFUND_REQUESTED);
            case 4 ->
                //주문 취소(ORDER_CANCELLED), 부분 환불(PAYMENT_PARTIALLY_REFUNDED)
                    orderDetail.orderStatus.in(OrderStatus.ORDER_CANCELLED, OrderStatus.PAYMENT_PARTIALLY_REFUNDED);
            default ->
                // 제공된 ID가 일치하지 않으면 필터를 적용하지 않도록 null 반환
                    null;
        };
    }
}
