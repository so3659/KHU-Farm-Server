package com.laicos.khufarm.global.common.exception;

import com.laicos.khufarm.global.common.base.BaseResponse;
import com.laicos.khufarm.global.common.exception.code.BaseCodeDTO;
import com.laicos.khufarm.global.common.exception.code.status.GlobalErrorStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        log.error("An error occurred: {}", e.getMessage(), e);
//        errorSender.sendError(e);
        return handleExceptionInternalFalse(GlobalErrorStatus._INTERNAL_SERVER_ERROR.getReason(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<String>> handleConstraintViolationException(ConstraintViolationException e){
        log.error("Validation error: {}", e.getMessage());
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return handleExceptionInternalFalse(GlobalErrorStatus._VALIDATION_ERROR.getReason(), errorMessage);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        log.error("An error occurred: {}", e.getMessage(), e);

        return handleExceptionInternalArgs(GlobalErrorStatus._VALIDATION_ERROR.getReason(), errors);

    }

    private ResponseEntity<BaseResponse<String>> handleExceptionInternalFalse(BaseCodeDTO errorCode, String errorPoint) {
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(BaseResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errorPoint));
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(BaseCodeDTO errorCode, Map<String, String> errorArgs) {
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(BaseResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errorArgs));
    }
}
