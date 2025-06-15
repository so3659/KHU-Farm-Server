package com.laicos.khufarm.global.common.exception.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class BaseCodeDTO {
    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
