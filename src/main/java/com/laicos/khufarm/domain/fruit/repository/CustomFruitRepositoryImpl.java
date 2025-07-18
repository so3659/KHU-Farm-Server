package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseIsWish;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.laicos.khufarm.domain.fruit.entity.QFruit.fruit;
import static com.laicos.khufarm.domain.wishList.entity.QWishList.wishList;

@Repository
@RequiredArgsConstructor
public class CustomFruitRepositoryImpl implements CustomFruitRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<FruitResponseIsWish> getFruitByConditions(User user, Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable){

        List<Fruit> fruitList = jpaQueryFactory.selectFrom(fruit)
                .leftJoin(fruit.seller).fetchJoin()
                .leftJoin(fruit.fruitCategory).fetchJoin()
                .leftJoin(fruit.wholesaleRetailCategory).fetchJoin()
                .where(
                        gtCursorId(cursorId), // 커서 조건
                        eqWholesaleRetailCategory(fruitReadCondition.getWholesaleRetailCategoryId()), // 도매/소매 카테고리 조건
                        eqFruitCategory(fruitReadCondition.getFruitCategoryId()), // 과일 카테고리 조건
                        searchKeywordCondition(fruitReadCondition.getSearchKeyword())
                )
                .orderBy(fruit.id.asc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        List<WishList> wishListList = jpaQueryFactory.selectFrom(wishList)
                .leftJoin(wishList.fruit).fetchJoin() // WishList와 Fruit을 조인
                .leftJoin(wishList.user).fetchJoin() // Fruit과 User 조인
                .where(
                        eqUserId(user.getId()) //사용자 ID 조건
                )
                .orderBy(wishList.id.asc())
                .fetch();

        Map<Long, WishList> wishFruitMap = wishListList.stream()
                .collect(Collectors.toMap(
                        wish -> wish.getFruit().getId(),
                        wish -> wish
                ));

        List<FruitResponseIsWish> content = FruitConverter.toFruitIsWishDTOList(fruitList, wishFruitMap);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqUserId(Long userId) {
        return (userId == null) ? null : wishList.user.id.eq(userId);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : fruit.id.gt(cursorId);
    }


    private BooleanExpression eqWholesaleRetailCategory(Long wholesaleRetailCategoryId) {
        return (wholesaleRetailCategoryId == null) ? null : fruit.wholesaleRetailCategory.id.eq(wholesaleRetailCategoryId);
    }

    private BooleanExpression eqFruitCategory(Long fruitCategoryId) {
        return (fruitCategoryId == null) ? null : fruit.fruitCategory.id.eq(fruitCategoryId);
    }

    private BooleanExpression searchKeywordCondition(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return null; // 검색어가 없으면 조건 없음
        }

        return fruit.title.contains(searchKeyword)
                .or(fruit.description.contains(searchKeyword))
                .or(fruit.seller.brandName.contains(searchKeyword));
    }
}


