package com.laicos.khufarm.global.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"timestamp", "isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    private final LocalDateTime timestamp;
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    private BaseResponse(Boolean isSuccess, String code, String message, T result) {
        this.timestamp = LocalDateTime.now();
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> BaseResponse<T> onSuccess(T result) {
        return new BaseResponse<>(true, "COMMON200", "요청에 성공하였습니다.", result);
    }

    public static <T> BaseResponse<T> onFailure(String code, String message, T data) {
        return new BaseResponse<>(false, code, message, data);
    }
}