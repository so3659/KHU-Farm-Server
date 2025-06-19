package com.laicos.khufarm.domain.seller.service;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.global.common.base.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SellerQueryService {

    BaseResponse<Slice<SellerResponse>> getSellers(Long cursorId, FruitReadCondition fruitReadCondition, Pageable pageable);
}
