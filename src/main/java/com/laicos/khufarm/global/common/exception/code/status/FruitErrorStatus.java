package com.laicos.khufarm.global.common.exception.code.status;

import com.laicos.khufarm.global.common.exception.code.BaseCodeDto;
import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FruitErrorStatus implements BaseCodeInterface {

    FRUIT_NOT_FOUND(HttpStatus.NOT_FOUND, "FRUIT404", "해당 과일을 찾을 수 없습니다."),
    FRUIT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FRUIT_CATEGORY404", "존재하지 않는 과일 카테고리입니다."),
    WHOLESALE_RETAIL_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "WHOLESALE_RETAIL_CATEGORY404", "존재하지 않는 도매/소매 카테고리입니다."),
    ;

    private final HttpStatus httpStatus;
    private final boolean isSuccess = false;
    private final String code;
    private final String message;

    @Override
    public BaseCodeDto getReason() {
        return BaseCodeDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
