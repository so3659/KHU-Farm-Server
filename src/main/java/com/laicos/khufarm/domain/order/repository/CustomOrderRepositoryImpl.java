package com.laicos.khufarm.domain.order.repository;

import com.laicos.khufarm.domain.order.converter.OrderDetailConverter;
import com.laicos.khufarm.domain.order.dto.response.OrderResponseWithDetail;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
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

    @Override
    public Slice<OrderResponseWithDetail> getOrderBySeller(User user, Long cursorId, Pageable pageable){

        Seller seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SellerErrorStatus.SELLER_NOT_FOUND));

        List<OrderDetail> orderDetailList = jpaQueryFactory.selectFrom(orderDetail)
                .leftJoin(orderDetail.order).fetchJoin()
                .leftJoin(orderDetail.fruit).fetchJoin()
                .leftJoin(orderDetail.review).fetchJoin()
                .where(
                        eqSellerId(seller.getId()), // 판매자 ID 조건
                        gtCursorId(cursorId) // 커서 조건
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

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : orderDetail.id.gt(cursorId);
    }

}
