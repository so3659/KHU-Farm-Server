package com.laicos.khufarm.domain.cart.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.cart.dto.response.CartResponse;
import com.laicos.khufarm.domain.cart.service.CartCommandService;
import com.laicos.khufarm.domain.cart.service.CartQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Validated
@Tag(name = "Cart", description = "장바구니 관련 API")
public class CartController {

    private final CartCommandService cartCommandService;
    private final CartQueryService cartQueryService;

    @PostMapping("/{fruitId}/add")
    public BaseResponse<Void> addCart(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("fruitId") Long fruitId,
            @RequestParam @Valid @NotNull(message = "수량은 필수입니다.") @Min(1) Integer count)
    {
        cartCommandService.addCart(customUserDetails.getUser(), fruitId, count);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping
    public BaseResponse<CartResponse> getCart(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);
        CartResponse cartResponses = cartQueryService.getCart(cursorId, customUserDetails.getUser(), pageable);

        return BaseResponse.onSuccess(cartResponses);
    }

    @DeleteMapping("/{cartId}/delete")
    public BaseResponse<Void> deleteWishList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("cartId") Long cartId)
    {
        cartCommandService.deleteCart(customUserDetails.getUser(), cartId);

        return BaseResponse.onSuccess(null);
    }
}
