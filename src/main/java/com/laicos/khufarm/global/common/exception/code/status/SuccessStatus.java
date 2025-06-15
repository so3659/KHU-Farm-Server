package com.laicos.khufarm.global.common.exception.code.status;

import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import com.laicos.khufarm.global.common.exception.code.BaseCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCodeInterface {

    _OK(HttpStatus.OK, "COMMON200", "성공입니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess = true;
    private final String code;
    private final String message;

    @Override
    public BaseCodeDTO getReason() {
        return BaseCodeDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
