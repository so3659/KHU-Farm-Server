package com.laicos.khufarm.global.common.exception;

import com.laicos.khufarm.global.common.exception.code.BaseCodeDto;
import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import org.springframework.http.HttpStatus;

public class RestApiException extends RuntimeException {

    private final BaseCodeInterface errorCode; //추상화 시킨 인터페이스를 받아서 사용
    private final HttpStatus httpStatus; // 재정의할 HttpStatus를 담을 필드

    // 기존 생성자: Enum에 정의된 기본 HttpStatus를 사용
    public RestApiException(BaseCodeInterface errorCode) {
        super(errorCode.getReason().getMessage()); // 예외 메시지 설정
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getReason().getHttpStatus(); // 기본값 사용
    }

    // 새로운 생성자: HttpStatus를 직접 지정
    public RestApiException(BaseCodeInterface errorCode, HttpStatus httpStatus) {
        super(errorCode.getReason().getMessage()); // 예외 메시지 설정
        this.errorCode = errorCode;
        this.httpStatus = httpStatus; // 전달받은 값으로 재정의
    }

    //추상화 시킨 ErrorCode의 getrCode()를 사용하여 ErrorCode를 반환
    public BaseCodeDto getErrorCode() {
        return this.errorCode.getReason();
    }
}
