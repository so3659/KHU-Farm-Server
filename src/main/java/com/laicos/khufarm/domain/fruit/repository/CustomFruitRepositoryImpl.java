package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.fruit.entity.QFruit.fruit;

@Repository
@RequiredArgsConstructor
public class CustomFruitRepositoryImpl implements CustomFruitRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<FruitResponse> getFruitByConditions(Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable){

        List<Fruit> fruitList = jpaQueryFactory.selectFrom(fruit)
                .leftJoin(fruit.seller).fetchJoin()
                .leftJoin(fruit.fruitCategory).fetchJoin()
                .leftJoin(fruit.wholesaleRetailCategory).fetchJoin()
                .where(
                        gtCursorId(cursorId), // 커서 조건
                        eqWholesaleRetailCategory(fruitReadCondition.getWholesaleRetailCategoryId()), // 도매/소매 카테고리 조건
                        eqFruitCategory(fruitReadCondition.getFruitCategoryId()), // 과일 카테고리 조건
                        eqTitle(fruitReadCondition.getSearchKeyword()), // 제목에 검색어 포함
                        eqDescription(fruitReadCondition.getSearchKeyword()) // 내용에 검색어 포함

                )
                .orderBy(fruit.id.asc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        List<FruitResponse> content = FruitConverter.toDTOList(fruitList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
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

    // 제목에 searchKeyword 포함되어있는지 필터링
    private BooleanExpression eqTitle(String searchKeyword) {
        return (searchKeyword == null) ? null : fruit.title.contains(searchKeyword);
    }

    // 내용에 searchKeyword 포함되어있는지 필터링
    private BooleanExpression eqDescription(String searchKeyword) {
        return (searchKeyword == null) ? null : fruit.description.contains(searchKeyword);
    }
}


