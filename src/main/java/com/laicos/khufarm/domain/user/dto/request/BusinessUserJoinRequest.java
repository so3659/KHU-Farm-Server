package com.laicos.khufarm.domain.user.dto.request;

import com.laicos.khufarm.domain.businessInfo.dto.BusinessInfoDto;
import com.laicos.khufarm.domain.termsAgreement.dto.TermsAgreementDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusinessUserJoinRequest {

    @NotNull(message = "이름은 필수 입력값입니다.")
    private String name;

    @Email
    private String email;

    @NotNull(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "올바른 아이디 형식이 아닙니다")
    private String userId;

    @NotNull(message = "휴대폰 번호는 필수 입력값입니다.")
    @Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @NotNull(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotNull(message = "비밀번호 확인은 필수 입력값입니다.")
    private String passwordConfirm;

    @NotNull(message = "버전은 필수 입력값입니다.")
    private double version;

    @NotNull(message = "약관 동의는 필수 입력값입니다.")
    private List<TermsAgreementDTO> termsAgreed;

    @NotNull(message = "사업자 정보는 필수 입력값입니다.")
    @Valid
    private BusinessInfoDto businessInfoDto;
}
