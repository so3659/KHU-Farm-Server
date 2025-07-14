package com.laicos.khufarm.domain.image.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ImageStatus {

    TEMP("임시","0"),
    USED("정식","1"),
    ;

    private final String description;
    private final String code;

    public static ImageStatus ofCode(String code){

        return Arrays.stream(ImageStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}
