package com.laicos.khufarm.domain.point.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PointEventType {

    REVIEW("리뷰 작성", "1"),
    ATTENDANCE("출석 체크", "2"),
    WALKING("만보기", "3");

    private final String description;
    private final String code;

    public static PointEventType ofCode(String code) {
        return Arrays.stream(PointEventType.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}
