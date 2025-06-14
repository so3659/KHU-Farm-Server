package com.laicos.khufarm.domain.user.enums;

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
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 필수 약관 코드입니다: %s", code)));
    }
}
