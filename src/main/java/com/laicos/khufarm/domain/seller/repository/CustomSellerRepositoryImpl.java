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
                        gtCursorId(cursorId),
                        searchKeywordCondition(sellerReadCondition.getSearchKeyword())
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

    private BooleanExpression searchKeywordCondition(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return null; // 검색어가 없으면 조건 없음
        }

        return seller.brandName.contains(searchKeyword)
                .or(seller.title.contains(searchKeyword))
                .or(seller.description.contains(searchKeyword));
    }
}
