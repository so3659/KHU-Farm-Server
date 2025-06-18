package com.laicos.khufarm.domain.fruit.apiSpecification.Fruit;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Slice;

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
    BaseResponse<Slice<FruitResponse>> getFruits(
            @Parameter(description = "커서 ID") Long cursorId,
            @Parameter(description = "도매/소매 카테고리 ID") Long wholesaleRetailCategoryId,
            @Parameter(description = "과일 카테고리 ID") Long fruitCategoryId,
            @Parameter(description = "페이지 크기 (default: 5)") int size);
}