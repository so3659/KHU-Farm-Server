package com.laicos.khufarm.domain.order.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefundRequest {

    private String refundReason;
}
