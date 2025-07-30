package com.laicos.khufarm.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PasswordChangeRequest {

    @NotNull(message = "현재 비밀번호는 필수 입력값입니다.")
    private String currentPassword;

    @NotNull(message = "새 비밀번호는 필수 입력값입니다.")
    private String newPassword;

    @NotNull(message = "새 비밀번호 확인은 필수 입력값입니다.")
    private String confirmNewPassword;

}
