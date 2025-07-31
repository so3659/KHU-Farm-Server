package com.laicos.khufarm.domain.delivery.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.delivery.dto.request.DeliveryTrackerWebhookRequest;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryInfoConfirmResponse;
import com.laicos.khufarm.domain.delivery.service.DeliveryCommandService;
import com.laicos.khufarm.domain.delivery.service.DeliveryQueryService;
import com.laicos.khufarm.domain.openfeign.client.DeliveryInfoConfirm;
import com.laicos.khufarm.domain.order.service.OrderCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
@Validated
@Tag(name = "Delivery", description = "배송 관련 API")
public class DeliveryController {

    private final DeliveryCommandService deliveryCommandService;
    private final DeliveryQueryService deliveryQueryService;
    private final DeliveryInfoConfirm deliveryInfoConfirm;
    private final OrderCommandService orderCommandService;

    @PatchMapping("/{orderDetailId}")
    public BaseResponse<Void> updateDeliveryStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody DeliveryInfoRequest deliveryInfoRequest,
            @PathVariable Long orderDetailId) {

        deliveryCommandService.updateDeliveryStatus(
                customUserDetails.getUser(),
                orderDetailId,
                deliveryInfoRequest);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{orderDetailId}/tracking")
    public BaseResponse<DeliveryInfoConfirmResponse> getDeliveryInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long orderDetailId
    ) {
        DeliveryInfoConfirmResponse deliveryInfoConfirmResponse = deliveryQueryService.getDeliveryInfo(
                customUserDetails.getUser(),
                orderDetailId);

        return BaseResponse.onSuccess(deliveryInfoConfirmResponse);
    }

    @PostMapping("/tracker/webhook")
    public ResponseEntity<Void> trackerWebhook(
            @RequestBody DeliveryTrackerWebhookRequest request) {

        deliveryCommandService.handleDeliveryStatusCallback(request.getCarrierId(), request.getTrackingNumber());

        return ResponseEntity.accepted().build();
    }
}

