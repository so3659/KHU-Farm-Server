package com.laicos.khufarm.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserStatus {

    ACTIVE("활성화","1"),
    DELETED("삭제","2");

    private final String description;
    private final String code;

    public static UserStatus ofCode(String code){

        return Arrays.stream(UserStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}

