package com.laicos.khufarm.global.common.exception;

import com.laicos.khufarm.global.common.base.BaseResponse;
import com.laicos.khufarm.global.common.exception.code.BaseCodeDTO;
import com.laicos.khufarm.global.common.exception.code.status.GlobalErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        log.error("An error occurred: {}", e.getMessage(), e);
//        errorSender.sendError(e);
        return handleExceptionInternalFalse(GlobalErrorStatus._INTERNAL_SERVER_ERROR.getReason(), e.getMessage());
    }

    private ResponseEntity<BaseResponse<String>> handleExceptionInternalFalse(BaseCodeDTO errorCode, String errorPoint) {
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(BaseResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errorPoint));
    }
}
