package com.laicos.khufarm.domain.seller.service;

import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponseWithFruit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SellerQueryService {

    Slice<SellerResponse> getSellers(Long cursorId, SellerReadCondition sellerReadCondition, Pageable pageable);
    SellerResponseWithFruit getFruitBySellerId(Long cursorId, Long wholesaleRetailCategoryId, Long sellerId, Pageable pageable);
}
