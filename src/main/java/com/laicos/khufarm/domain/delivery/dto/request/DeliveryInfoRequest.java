package com.laicos.khufarm.domain.delivery.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeliveryInfoRequest {

    @NotNull(message = "배송 회사는 필수 항목입니다.")
    private String deliveryCompany;

    @NotNull(message = "배송 번호는 필수 항목입니다.")
    private String deliveryNumber;
}
