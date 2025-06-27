package com.laicos.khufarm.global.common.exception.code.status;

import com.laicos.khufarm.global.common.exception.code.BaseCodeDto;
import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessInfoErrorStatus implements BaseCodeInterface {

    BUSINESS_ALREADY_EXISTS(HttpStatus.CONFLICT, "BUSINESS409", "이미 존재하는 사업자입니다."),
    INVALID_BUSINESS_INFO(HttpStatus.BAD_REQUEST, "BUSINESS404", "유효하지 않은 사업자 정보입니다."),
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
