package com.laicos.khufarm.domain.seller.repository;

import com.laicos.khufarm.domain.seller.converter.SellerConverter;
import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.seller.entity.QSeller.seller;

@Repository
@RequiredArgsConstructor
public class CustomSellerRepositoryImpl implements CustomSellerRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<SellerResponse> getSellerByCondition(Long cursorId, SellerReadCondition sellerReadCondition, Pageable pageable){

        List<Seller> sellerList = jpaQueryFactory.selectFrom(seller)
                .where(
                        gtCursorId(cursorId), // 커서 조건
                        eqBrandName(sellerReadCondition.getSearchKeyword()), // 농가 이름에 검색어 포함
                        eqTitle(sellerReadCondition.getSearchKeyword()), // 제목에 검색어 포함
                        eqDescription(sellerReadCondition.getSearchKeyword()) // 내용에 검색어 포함

                )
                .orderBy(seller.id.asc())
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

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : seller.id.gt(cursorId);
    }

    // 농가 이름에 searchKeyword 포함되어있는지 필터링
    private BooleanExpression eqBrandName(String searchKeyword) {
        return (searchKeyword == null) ? null : seller.brandName.contains(searchKeyword);
    }

    // 제목에 searchKeyword 포함되어있는지 필터링
    private BooleanExpression eqTitle(String searchKeyword) {
        return (searchKeyword == null) ? null : seller.title.contains(searchKeyword);
    }

    // 내용에 searchKeyword 포함되어있는지 필터링
    private BooleanExpression eqDescription(String searchKeyword) {
        return (searchKeyword == null) ? null : seller.description.contains(searchKeyword);
    }
}
