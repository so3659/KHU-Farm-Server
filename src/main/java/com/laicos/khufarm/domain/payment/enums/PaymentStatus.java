package com.laicos.khufarm.domain.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PAYMENT_COMPLETED("결제 완료", "1"),
    PAYMENT_FAILED("결제 실패","2"),
    PAYMENT_CANCELLED("결제 취소", "3"),
    ;

    private final String description;
    private final String code;

    public static PaymentStatus ofCode(String code) {
        return Arrays.stream(PaymentStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}
