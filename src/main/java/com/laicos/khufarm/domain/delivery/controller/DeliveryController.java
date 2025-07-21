package com.laicos.khufarm.domain.delivery.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryInfoConfirmResponse;
import com.laicos.khufarm.domain.delivery.service.DeliveryCommandService;
import com.laicos.khufarm.domain.delivery.service.DeliveryQueryService;
import com.laicos.khufarm.domain.openfeign.client.DeliveryInfoConfirm;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PatchMapping("/{orderId}")
    public BaseResponse<Void> updateDeliveryStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody DeliveryInfoRequest deliveryInfoRequest,
            @PathVariable Long orderId) {

        deliveryCommandService.updateDeliveryStatus(
                customUserDetails.getUser(),
                orderId,
                deliveryInfoRequest);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{orderId}/tracking")
    public BaseResponse<DeliveryInfoConfirmResponse> getDeliveryInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long orderId
    ) {
        DeliveryInfoConfirmResponse deliveryInfoConfirmResponse = deliveryQueryService.getDeliveryInfo(
                customUserDetails.getUser(),
                orderId);

        return BaseResponse.onSuccess(deliveryInfoConfirmResponse);
    }
}
