package com.laicos.khufarm.domain.termsAgreement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TermsAgreementDTO {

    @NotNull(message = "약관 ID는 필수입니다.")
    private Long termsConditionsId;

    @NotNull(message = "동의 여부는 필수입니다.")
    private Boolean agreed;
}
