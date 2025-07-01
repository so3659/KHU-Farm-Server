package com.laicos.khufarm.domain.wishList.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.wishList.service.WishListCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
@Validated
@Tag(name = "WishList", description = "찜 관련 API")
public class WishListController {

    private final WishListCommandService wishListCommandService;

    @PostMapping("/{fruitId}/add")
    public BaseResponse<Void> addWishList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("fruitId") Long fruitId)
    {
        wishListCommandService.addWishList(customUserDetails.getUser(), fruitId);

        return BaseResponse.onSuccess(null);
    }

}
