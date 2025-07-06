package com.laicos.khufarm.domain.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

public class OrderRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CartOrderRequest {

        @NotNull(message = "장바구니 ID는 필수입니다.")
        private List<Long> cartId;

        @NotNull(message = "총 가격은 필수입니다.")
        private Integer totalPrice;

        @Valid
        @NotNull
        private ShippingInfo shippingInfo;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class DirectOrderRequest {

        @NotNull(message = "상품 ID는 필수입니다.")
        private Long fruitId;

        @NotNull(message = "주문 수량은 필수입니다.")
        private Integer orderCount;

        @NotNull(message = "총 가격은 필수입니다.")
        private Integer totalPrice;

        @Valid
        @NotNull
        private ShippingInfo shippingInfo;
    }
}
