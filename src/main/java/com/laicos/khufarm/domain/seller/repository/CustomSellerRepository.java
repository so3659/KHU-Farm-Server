package com.laicos.khufarm.domain.seller.repository;

import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomSellerRepository {
    Slice<SellerResponse> getSellerByCondition(Long cursorId, SellerReadCondition sellerReadCondition, Pageable pageable);
}
