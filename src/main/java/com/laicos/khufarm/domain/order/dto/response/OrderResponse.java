package com.laicos.khufarm.domain.order.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderResponse {

    private String merchantUid;
    private String ordererName;
    private Integer totalPrice;
    private Integer orderCount;
    private String portCode;
    private String address;
    private String detailAddress;
    private String recipient;
    private String phoneNumber;
    private String orderRequest;

}
