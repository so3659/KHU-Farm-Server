package com.laicos.khufarm.domain.fruit.contoller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.fruit.apiSpecification.FruitApiSpecification;
import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.service.FruitCommandService;
import com.laicos.khufarm.domain.fruit.service.FruitQueryService;
import com.laicos.khufarm.domain.fruit.validation.annotation.ExistFruitCategory;
import com.laicos.khufarm.domain.fruit.validation.annotation.ExistWholesaleRetailCategory;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fruits")
@RequiredArgsConstructor
@Validated
@Tag(name = "Fruit", description = "과일 관련 API")
public class FruitController implements FruitApiSpecification {

    private final FruitQueryService fruitQueryService;
    private final FruitCommandService fruitCommandService;

    @GetMapping
    public BaseResponse<Slice<FruitResponse>> getFruits(
            @RequestParam(required = false) Long cursorId,
            @ExistWholesaleRetailCategory @RequestParam Long wholesaleRetailCategoryId,
            @ExistFruitCategory @RequestParam Long fruitCategoryId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<FruitResponse> fruitResponses = fruitQueryService.getFruitList(cursorId, new FruitReadCondition(wholesaleRetailCategoryId, fruitCategoryId), pageable);

        return BaseResponse.onSuccess(fruitResponses);
    }

    @GetMapping("/search")
    public BaseResponse<Slice<FruitResponse>> searchFruits(
            @RequestParam(required = false) Long cursorId,
            @ExistWholesaleRetailCategory @RequestParam Long wholesaleRetailCategoryId,
            @ExistFruitCategory @RequestParam Long fruitCategoryId,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<FruitResponse> fruitResponses = fruitQueryService.getFruitList(cursorId, new FruitReadCondition(wholesaleRetailCategoryId, fruitCategoryId, searchKeyword), pageable);

        return BaseResponse.onSuccess(fruitResponses);
    }

    @PostMapping("/add")
    public BaseResponse<Void> addFruit(
            @Valid @RequestBody FruitAddRequest fruitAddRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        fruitCommandService.addFruit(customUserDetails.getUser(), fruitAddRequest);

        return BaseResponse.onSuccess(null);
    }

}