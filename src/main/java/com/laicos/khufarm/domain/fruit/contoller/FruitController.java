package com.laicos.khufarm.domain.fruit.contoller;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.service.FruitQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
@RequiredArgsConstructor
public class FruitController {

    private final FruitQueryService fruitQueryService;

    @GetMapping
    public BaseResponse<Slice<FruitResponse>> getFruits(
            @RequestParam(required = false) Long cursorId,
            @RequestParam Long wholesaleRetailCategoryId,
            @RequestParam Long fruitCategoryId,
            @ParameterObject int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<FruitResponse> fruitResponses = fruitQueryService.getFruitList(cursorId, new FruitReadCondition(wholesaleRetailCategoryId, fruitCategoryId), pageable);

        return BaseResponse.onSuccess(fruitResponses);
    }
}
