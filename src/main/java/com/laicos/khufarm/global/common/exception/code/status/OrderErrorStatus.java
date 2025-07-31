package com.laicos.khufarm.global.common.exception.code.status;

import com.laicos.khufarm.global.common.exception.code.BaseCodeDto;
import com.laicos.khufarm.global.common.exception.code.BaseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorStatus implements BaseCodeInterface {

    PRICE_NOT_MATCH(HttpStatus.BAD_REQUEST, "ORDER400", "가격이 일치하지 않습니다"),
    OUT_OF_STOCK(HttpStatus.SERVICE_UNAVAILABLE, "ORDER401", "재고가 부족합니다"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404", "주문을 찾을 수 없습니다"),
    ORDER_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_DETAIL404", "해당 주문 상세을 찾을 수 없습니다"),
    DELIVERY_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "DELIVERY404", "배송 정보를 찾을 수 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final boolean isSuccess = false;
    private final String code;
    private final String message;

    @Override
    public BaseCodeDto getReason() {
        return BaseCodeDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(isSuccess)
                .code(code)
                .message(message)
                .build();
    }
}
