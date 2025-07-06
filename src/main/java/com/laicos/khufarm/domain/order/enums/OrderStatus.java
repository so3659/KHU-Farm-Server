package com.laicos.khufarm.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PAYMENT_STANDBY("결제 대기", "1"),
    ORDER_COMPLETED("주문 완료", "2"),
    PREPARING_SHIPMENT("배송 준비중","3"),
    SHIPPING("배송중", "4"),
    SHIPMENT_COMPLETED("배송 완료", "5"),
    ORDER_CANCELLED("주문 취소", "6"),
    ORDER_FAILED("주문 실패", "7"),
    ;

    private final String description;
    private final String code;

    public static OrderStatus ofCode(String code) {
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}
