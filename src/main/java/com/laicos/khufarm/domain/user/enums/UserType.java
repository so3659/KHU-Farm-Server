package com.laicos.khufarm.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {

    ROLE_INDIVIDUAL("개인","1"),
    ROLE_BUSINESS("기업","2"),
    ROLE_FARMER("농가 회원","3"),
    ROLE_ADMIN("관리자","4");

    private final String description;
    private final String code;

    public static UserType ofCode(String code){

        return Arrays.stream(UserType.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}

