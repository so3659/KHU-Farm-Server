package com.laicos.khufarm.global.common.exception.code.status;

import com.laicos.khufarm.global.common.exception.code.BaseCodeDto;
import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressErrorStatus implements BaseCodeInterface {

    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ATTEND404", "주소를 찾을 수 없습니다."),
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

