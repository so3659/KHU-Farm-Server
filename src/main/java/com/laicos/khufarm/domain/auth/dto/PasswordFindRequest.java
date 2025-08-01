package com.laicos.khufarm.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PasswordFindRequest {

    @NotNull(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotNull(message = "아이디는 필수 입력값입니다.")
    private String userId;
}
