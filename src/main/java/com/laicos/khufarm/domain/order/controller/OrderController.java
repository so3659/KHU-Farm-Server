package com.laicos.khufarm.domain.order.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.order.dto.request.OrderRequest;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.service.OrderCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrderCommandService orderCommandService;

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
}
