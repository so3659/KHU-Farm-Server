package com.laicos.khufarm.domain.seller.repository;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.seller.converter.SellerConverter;
import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponseWithFruit;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.fruit.entity.QFruit.fruit;
import static com.laicos.khufarm.domain.seller.entity.QSeller.seller;

@Repository
@RequiredArgsConstructor
public class CustomSellerRepositoryImpl implements CustomSellerRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<SellerResponse> getSellerByCondition(Long cursorId, SellerReadCondition sellerReadCondition, Pageable pageable){

        List<Seller> sellerList = jpaQueryFactory.selectFrom(seller)
                .where(
                        gtCursorId(cursorId),
                        searchKeywordCondition(sellerReadCondition.getSearchKeyword())
                )
                .orderBy(seller.id.desc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        List<SellerResponse> content = SellerConverter.toDTOList(sellerList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }


    @Override
    public SellerResponseWithFruit getFruitBySellerId(Long cursorId, Long wholesaleRetailCategoryId, Long sellerId, Pageable pageable){

        Seller foundSeller = jpaQueryFactory.selectFrom(seller)
                .where(seller.id.eq(sellerId))
                .fetchOne();

        List<Fruit> fruitList = jpaQueryFactory.selectFrom(fruit)
                .leftJoin(fruit.seller).fetchJoin()
                .leftJoin(fruit.wholesaleRetailCategory).fetchJoin()
                .where(
                        gtCursorId(cursorId), // 커서 조건
                        eqWholesaleRetailCategory(wholesaleRetailCategoryId), // 도매/소매 카테고리 조건
                        eqSellerId(sellerId) // 판매자 ID 조건
                )
                .orderBy(fruit.id.desc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        List<FruitResponse> content = FruitConverter.toFruitDTOList(fruitList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        Slice<FruitResponse> fruits = new SliceImpl<>(content, pageable, hasNext);

        return SellerConverter.toDTOList(foundSeller, fruits);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : seller.id.gt(cursorId);
    }


    private BooleanExpression eqSellerId(Long sellerId) {
        return (sellerId == null) ? null : fruit.seller.id.eq(sellerId);
    }

    private BooleanExpression eqWholesaleRetailCategory(Long wholesaleRetailCategoryId) {
        return (wholesaleRetailCategoryId == null) ? null : fruit.wholesaleRetailCategory.id.eq(wholesaleRetailCategoryId);
    }

    private BooleanExpression searchKeywordCondition(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return null; // 검색어가 없으면 조건 없음
        }

        return seller.brandName.contains(searchKeyword)
                .or(seller.title.contains(searchKeyword))
                .or(seller.description.contains(searchKeyword));
    }
}
