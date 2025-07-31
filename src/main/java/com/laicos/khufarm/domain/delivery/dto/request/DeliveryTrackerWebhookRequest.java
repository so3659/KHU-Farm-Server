package com.laicos.khufarm.domain.delivery.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeliveryTrackerWebhookRequest {
    private String carrierId;
    private String trackingNumber;
}
