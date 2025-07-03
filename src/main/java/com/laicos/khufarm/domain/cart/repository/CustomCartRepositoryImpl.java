package com.laicos.khufarm.domain.cart.repository;

import com.laicos.khufarm.domain.cart.converter.CartConverter;
import com.laicos.khufarm.domain.cart.dto.response.CartResponse;
import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.cart.entity.QCart.cart;


@Repository
@RequiredArgsConstructor
public class CustomCartRepositoryImpl implements CustomCartRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CartResponse getCart(Long cursorId, User user, Pageable pageable){

        List<Cart> CartList = jpaQueryFactory.selectFrom(cart)
                .where(
                        eqUserId(user.getId()), //사용자 ID 조건
                        gtCursorId(cursorId) // 커서 조건
                )
                .orderBy(cart.id.asc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        List<Fruit> fruitList = CartList.stream()
                .map(Cart::getFruit)
                .toList();

        List<Integer> countList = CartList.stream()
                .map(Cart::getCount)
                .toList();

        List<FruitResponseWithCount> content = FruitConverter.toFruitDTOListWithCount(fruitList, countList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        Slice<FruitResponseWithCount> fruitsWithCount = new SliceImpl<>(content, pageable, hasNext);

        return CartConverter.toDTOList(user, fruitsWithCount);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : cart.id.gt(cursorId);
    }

    private BooleanExpression eqUserId(Long userId) {
        return (userId == null) ? null : cart.user.id.eq(userId);
    }
}
