package com.laicos.khufarm.domain.cart.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.cart.service.CartCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/{fruitId}/add")
    public BaseResponse<Void> addWishList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("fruitId") Long fruitId,
            @RequestParam @Valid @NotNull(message = "수량은 필수입니다.") @Min(1) Integer count)
    {
        cartCommandService.addCart(customUserDetails.getUser(), fruitId, count);

        return BaseResponse.onSuccess(null);
    }
}
