package com.laicos.khufarm.domain.termsAgreement.enums;

import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.TermsConditionsErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TermsEssential {

    ESSENTIAL("필수", "1"),
    OPTIONAL("선택", "2");

    private final String description;
    private final String code;

    public static TermsEssential ofCode(String code) {
        return Arrays.stream(TermsEssential.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new RestApiException(TermsConditionsErrorStatus.TERMS_CONDITIONS_NOT_FOUND)
                );
    }
}
