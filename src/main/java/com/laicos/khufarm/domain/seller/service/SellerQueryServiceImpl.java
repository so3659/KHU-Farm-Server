package com.laicos.khufarm.domain.seller.service;

import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponseWithFruit;
import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerQueryServiceImpl implements SellerQueryService{

    private final SellerRepository sellerRepository;

    @Override
    public Slice<SellerResponse> getSellers(Long cursorId, SellerReadCondition sellerReadCondition, Pageable pageable){

        return sellerRepository.getSellerByCondition(cursorId, sellerReadCondition, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SellerResponseWithFruit getFruitBySellerId(Long cursorId, Long wholesaleRetailCategoryId, Long sellerId, Pageable pageable){

        return sellerRepository.getFruitBySellerId(cursorId, wholesaleRetailCategoryId, sellerId, pageable);
    }
}
