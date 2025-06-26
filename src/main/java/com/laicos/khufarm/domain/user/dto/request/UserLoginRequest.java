package com.laicos.khufarm.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "올바른 아이디 형식이 아닙니다")
    private String userId;

    @NotNull(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
