package com.laicos.khufarm.domain.wishList.repository;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.converter.WishListConverter;
import com.laicos.khufarm.domain.wishList.dto.response.WishListResponse;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.wishList.entity.QWishList.wishList;

@Repository
@RequiredArgsConstructor
public class CustomWishListRepositoryImpl implements CustomWishListRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public WishListResponse getWishList(Long cursorId, User user, Pageable pageable){

        List<WishList> WishListList = jpaQueryFactory.selectFrom(wishList)
                .where(
                        eqUserId(user.getId()), //사용자 ID 조건
                        gtCursorId(cursorId) // 커서 조건
                )
                .orderBy(wishList.id.asc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        List<Fruit> fruitList = WishListList.stream()
                .map(WishList::getFruit)
                .toList();

        List<FruitResponse> content = FruitConverter.toDTOList(fruitList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        Slice<FruitResponse> fruits = new SliceImpl<>(content, pageable, hasNext);

        return WishListConverter.toDTOList(user, fruits);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : wishList.id.gt(cursorId);
    }

    private BooleanExpression eqUserId(Long userId) {
        return (userId == null) ? null : wishList.user.id.eq(userId);
    }
}
