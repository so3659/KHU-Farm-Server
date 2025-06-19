package com.laicos.khufarm.domain.seller.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
@Validated
@Tag(name = "Seller", description = "판매자 관련 API")
public class SellerController {
//
//    @GetMapping
//    @Transactional(readOnly = true)
//    public BaseResponse<Slice<SellerResponse>> getSellers(
//            @RequestParam(required = false) Long cursorId,
//            @RequestParam(defaultValue="5") int size) {
//
//        Pageable pageable = PageRequest.of(0, size);
//        Slice<FruitResponse> SellerResponses = fruitQueryService.getFruitList(cursorId, new FruitReadCondition(wholesaleRetailCategoryId, fruitCategoryId), pageable);
//
//        return BaseResponse.onSuccess(SellerResponses);
//    }

}
