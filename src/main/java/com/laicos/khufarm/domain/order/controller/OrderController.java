package com.laicos.khufarm.domain.order.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.order.dto.request.OrderRequest;
import com.laicos.khufarm.domain.order.dto.request.RefundRequest;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.dto.response.OrderResponseWithDetail;
import com.laicos.khufarm.domain.order.dto.response.PrePareOrderResponse;
import com.laicos.khufarm.domain.order.service.OrderCommandService;
import com.laicos.khufarm.domain.order.service.OrderQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @GetMapping("/prepare/cartOrder")
    public BaseResponse<PrePareOrderResponse> prepareCartOrder(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam List<Long> cartIds)
    {

        PrePareOrderResponse prePareOrderResponse = orderQueryService.getPrepareCartOrder(customUserDetails.getUser(), cartIds);

        return BaseResponse.onSuccess(prePareOrderResponse);
    }

    @GetMapping("/prepare/directOrder")
    public BaseResponse<PrePareOrderResponse> prepareDirectOrder(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long fruitId,
            @RequestParam Integer orderCount)
    {

        PrePareOrderResponse prePareOrderResponse = orderQueryService.getPrepareDirectOrder(customUserDetails.getUser(), fruitId, orderCount);

        return BaseResponse.onSuccess(prePareOrderResponse);
    }

    @PostMapping("/cartOrder")
    public BaseResponse<OrderResponse> orderByCart(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid OrderRequest.CartOrderRequest request)
    {

        OrderResponse orderResponse = orderCommandService.orderByCart(customUserDetails.getUser(), request);

        return BaseResponse.onSuccess(orderResponse);
    }

    @PostMapping("/directOrder")
    public BaseResponse<OrderResponse> orderByDirect(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid OrderRequest.DirectOrderRequest request)
    {

        OrderResponse orderResponse = orderCommandService.orderByDirect(customUserDetails.getUser(), request);

        return BaseResponse.onSuccess(orderResponse);
    }

    @GetMapping("/seller/orders/{orderStatusId}")
    public BaseResponse<Slice<OrderResponseWithDetail>> getOrdersBySeller(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long orderStatusId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);

        Slice<OrderResponseWithDetail> orderResponses = orderQueryService.getOrderBySeller(customUserDetails.getUser(), cursorId, pageable, orderStatusId);

        return BaseResponse.onSuccess(orderResponses);
    }

    @PatchMapping("/refund/{orderDetailId}")
    public BaseResponse<Void> refundOrder(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RefundRequest refundRequest,
            @PathVariable Long orderDetailId) throws FirebaseMessagingException {

        orderCommandService.refundOrder(customUserDetails.getUser(), orderDetailId, refundRequest);

        return BaseResponse.onSuccess(null);
    }
}
