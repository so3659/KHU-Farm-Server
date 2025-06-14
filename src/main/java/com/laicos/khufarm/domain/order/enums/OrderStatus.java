package com.laicos.khufarm.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PAYMENT_COMPLETED("결제 완료", "1"),
    PREPARING_SHIPMENT("배송 준비중","2"),
    SHIPPING("배송중", "3"),
    SHIPMENT_COMPLETED("배송 완료", "4"),
    ORDER_CANCELLED("주문 취소", "5");

    private final String description;
    private final String code;

    public static OrderStatus ofCode(String code) {
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상태 코드입니다: %s", code)));
    }
}
