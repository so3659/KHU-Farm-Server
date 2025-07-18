package com.laicos.khufarm.domain.point.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.point.service.PointCommandService;
import com.laicos.khufarm.domain.review.dto.ReviewReadCondition;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.review.service.ReviewQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
@Validated
@Tag(name = "Point", description = "포인트 획득 관련 API")
public class PointController {

    private final ReviewQueryService reviewQueryService;
    private final PointCommandService pointCommandService;

    @GetMapping("/retrieve/my")
    public BaseResponse<Slice<ReviewResponse>> getMyReviews(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);

        Slice<ReviewResponse> reviewResponses = reviewQueryService.getAllReviews(cursorId, new ReviewReadCondition(customUserDetails.getUser()), pageable);

        return BaseResponse.onSuccess(reviewResponses);
    }

    @PostMapping("/retrieve/my/{reviewId}")
    public BaseResponse<Void> getReviewPoint(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long reviewId) {
        pointCommandService.getReviewPoint(customUserDetails.getUser(), reviewId);
        return BaseResponse.onSuccess(null);
    }
}
