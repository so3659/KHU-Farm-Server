package com.laicos.khufarm.global.common.exception.code.status;

import com.laicos.khufarm.global.common.exception.code.BaseCodeDto;
import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorStatus implements BaseCodeInterface {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "해당 회원을 찾을 수 없습니다."),
    ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER409", "이미 존재하는 아이디입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "EMAIL409", "이미 존재하는 이메일입니다."),
    INVALID_LOGIN_TYPE(HttpStatus.BAD_REQUEST, "USER400", "잘못된 로그인 방식입니다."),
    USERTYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER403", "유저 타입을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER401", "비밀번호가 올바르지 않습니다."),
    INVALID_PASSWORD_CONFIRM(HttpStatus.BAD_REQUEST, "USER402", "비밀번호가 일지하지 않습니다."),
    USER_STAND_BY(HttpStatus.BAD_REQUEST, "USER409", "회원 가입 대기 중입니다."),
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

