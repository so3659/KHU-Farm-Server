package com.laicos.khufarm.domain.order.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderResponseWithDetail {

    private Long orderId;
    private Long orderDetailId;
    private String merchantUid;
    private String ordererName;
    private Integer totalPrice;
    private String fruitTitle;
    private Integer orderCount;
    private String portCode;
    private String address;
    private String detailAddress;
    private String recipient;
    private String phoneNumber;
    private String deliveryCompany;
    private String deliveryNumber;
    private String orderRequest;
    private String deliveryStatus;
    private String orderStatus;
    private String refundReason;
    private LocalDateTime createdAt;

}
