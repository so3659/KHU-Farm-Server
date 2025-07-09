package com.laicos.khufarm.domain.review.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.review.service.ReviewCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Validated
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;

    @PostMapping("/{orderDetailId}/add")
    public BaseResponse<Void> addReview(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid ReviewRequest reviewRequest,
            @PathVariable Long orderDetailId)
    {
        reviewCommandService.addReview(customUserDetails.getUser(), reviewRequest, orderDetailId);

        return BaseResponse.onSuccess(null);
    }
}
