package com.laicos.khufarm.domain.fruit.apiSpecification;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.validation.anootation.ExistFruitCategory;
import com.laicos.khufarm.domain.fruit.validation.anootation.ExistWholesaleRetailCategory;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestParam;

public interface FruitApiSpecification {

    @Operation(summary = "과일 목록 조회 API",
            description = "🔍 Cursor-based Pagination을 기반으로 과일 목록을 조회합니다.<br>💡 cursorId가 null이면 처음부터 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "과일 목록 조회 성공"),
            @ApiResponse(responseCode = "402", description = "카테고리 값 오류",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON402",
                                      "message": "Validation Error입니다.",
                                      "result": "존재하지 않는 도매/소매 카테고리입니다."
                                    }
                                    """)))
    })
    public BaseResponse<Slice<FruitResponse>> getFruits(
            @Parameter(description = "커서 ID") @RequestParam(required = false) Long cursorId,
            @Parameter(description = "도매/소매 카테고리 ID") @ExistWholesaleRetailCategory @RequestParam Long wholesaleRetailCategoryId,
            @Parameter(description = "과일 카테고리 ID") @ExistFruitCategory @RequestParam Long fruitCategoryId,
            @Parameter(description = "페이지 크기 (default: 5)") @RequestParam(defaultValue="5") int size);


    @Operation(summary = "과일 검색 API",
            description = "💡 키워드가 농가이름, 제목, 내용에 포함되어 있는 과일 목록을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "과일 목록 조회 성공"),
            @ApiResponse(responseCode = "402", description = "카테고리 값 오류",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "isSuccess": false,
                                      "code": "COMMON402",
                                      "message": "Validation Error입니다.",
                                      "result": "존재하지 않는 도매/소매 카테고리입니다."
                                    }
                                    """)))
    })
    public BaseResponse<Slice<FruitResponse>> searchFruits(
            @Parameter(description = "커서 ID") @RequestParam(required = false) Long cursorId,
            @Parameter(description = "도매/소매 카테고리 ID") @ExistWholesaleRetailCategory @RequestParam Long wholesaleRetailCategoryId,
            @Parameter(description = "과일 카테고리 ID") @ExistFruitCategory @RequestParam Long fruitCategoryId,
            @Parameter(description = "검색 키워드") @RequestParam(required = false) String searchKeyword,
            @Parameter(description = "페이지 크기 (default: 5)") @RequestParam(defaultValue="5") int size);
}