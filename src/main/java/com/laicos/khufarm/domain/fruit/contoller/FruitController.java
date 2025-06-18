package com.laicos.khufarm.domain.fruit.contoller;

import com.laicos.khufarm.domain.fruit.dto.FruitReadCondition;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.service.FruitQueryService;
import com.laicos.khufarm.domain.fruit.validation.anootation.ExistFruitCategory;
import com.laicos.khufarm.domain.fruit.validation.anootation.ExistWholesaleRetailCategory;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
@RequiredArgsConstructor
@Validated
public class FruitController {

    private final FruitQueryService fruitQueryService;

    @Tag(name = "Fruit", description = "과일 관련 API")
    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "과일 목록 조회 API", description = "Cursor-based Pagination을 기반으로 과일 목록을 조회하는 API입니다. cursorId가 null이면 처음부터 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON402", description = "파라미터 값 오류"),
    })
    public BaseResponse<Slice<FruitResponse>> getFruits(
            @Parameter(description = "커서 ID")
            @RequestParam(required = false) Long cursorId,

            @Parameter(description = "도매/소매 카테고리 ID")
            @ExistWholesaleRetailCategory
            @RequestParam Long wholesaleRetailCategoryId,

            @Parameter(description = "과일 카테고리 ID")
            @ExistFruitCategory
            @RequestParam Long fruitCategoryId,

            @Parameter(description = "한 페이지에 표시할 과일 개수 (default: 5)")
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<FruitResponse> fruitResponses = fruitQueryService.getFruitList(cursorId, new FruitReadCondition(wholesaleRetailCategoryId, fruitCategoryId), pageable);

        return BaseResponse.onSuccess(fruitResponses);
    }
}
