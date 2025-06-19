package com.laicos.khufarm.domain.seller.controller;

import com.laicos.khufarm.domain.seller.apiSpecification.SellerApiSpecification;
import com.laicos.khufarm.domain.seller.dto.SellerReadCondition;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.service.SellerQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
@Validated
@Tag(name = "Seller", description = "판매자 관련 API")
public class SellerController implements SellerApiSpecification {

    private final SellerQueryService sellerQueryService;

    @GetMapping
    public BaseResponse<Slice<SellerResponse>> getSellers(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<SellerResponse> SellerResponses = sellerQueryService.getSellers(cursorId, new SellerReadCondition(), pageable);

        return BaseResponse.onSuccess(SellerResponses);
    }

}
