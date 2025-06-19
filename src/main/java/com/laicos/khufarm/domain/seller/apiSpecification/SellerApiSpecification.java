package com.laicos.khufarm.domain.seller.apiSpecification;

import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestParam;

public interface SellerApiSpecification {

    @Operation(summary = "농가 목록 조회 API",
            description = "🔍 Cursor-based Pagination을 기반으로 농가 목록을 조회합니다.<br>💡 cursorId가 null이면 처음부터 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "농가 목록 조회 성공")
    })
    public BaseResponse<Slice<SellerResponse>> getSellers(
            @Parameter(description = "커서 ID") @RequestParam(required = false) Long cursorId,
            @Parameter(description = "페이지 크기 (default: 5)") @RequestParam(defaultValue="5") int size);

    @Operation(summary = "농가 검색 API",
            description = "💡 키워드가 농가 이름, 제목, 내용에 포함되어 있는 농가 목록을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "농가 목록 조회 성공")
    })
    public BaseResponse<Slice<SellerResponse>> searchSellers(
            @Parameter(description = "커서 ID") @RequestParam(required = false) Long cursorId,
            @Parameter(description = "검색 키워드") @RequestParam(required = false) String searchKeyword,
            @Parameter(description = "페이지 크기 (default: 5)") @RequestParam(defaultValue="5") int size);
}
