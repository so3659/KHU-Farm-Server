package com.laicos.khufarm.domain.wishList.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.wishList.dto.response.WishListResponse;
import com.laicos.khufarm.domain.wishList.service.WishListCommandService;
import com.laicos.khufarm.domain.wishList.service.WishListQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final WishListQueryService wishListQueryService;

    @PostMapping("/{fruitId}/add")
    public BaseResponse<Long> addWishList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("fruitId") Long fruitId)
    {
        Long wishListId=wishListCommandService.addWishList(customUserDetails.getUser(), fruitId);

        return BaseResponse.onSuccess(wishListId);
    }

    @GetMapping
    public BaseResponse<WishListResponse> getWishList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);
        WishListResponse wishListResponses = wishListQueryService.getWishList(cursorId, customUserDetails.getUser(), pageable);

        return BaseResponse.onSuccess(wishListResponses);
    }

    @DeleteMapping("/{wishListId}/delete")
    public BaseResponse<Void> deleteWishList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("wishListId") Long wishListId)
    {
        wishListCommandService.deleteWishList(customUserDetails.getUser(), wishListId);

        return BaseResponse.onSuccess(null);
    }
}
