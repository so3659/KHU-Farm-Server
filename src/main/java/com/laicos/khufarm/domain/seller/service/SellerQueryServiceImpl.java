package com.laicos.khufarm.domain.seller.service;

import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerQueryServiceImpl implements SellerQueryService{

    private final SellerRepository sellerRepository;

    @Override
    public Slice<SellerResponse> getSellers(Long cursorId, SellerReadCondition sellerReadCondition, Pageable pageable){

        return sellerRepository.getSellerByCondition(cursorId, sellerReadCondition, pageable);
    }
}
