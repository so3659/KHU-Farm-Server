package com.laicos.khufarm.domain.review.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.review.dto.ReviewReadCondition;
import com.laicos.khufarm.domain.review.dto.request.ReviewReplyRequest;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.review.dto.response.MyReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.review.service.ReviewCommandService;
import com.laicos.khufarm.domain.review.service.ReviewQueryService;
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

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Validated
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("/{orderDetailId}/add")
    public BaseResponse<Void> addReview(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid ReviewRequest reviewRequest,
            @PathVariable Long orderDetailId)
    {
        reviewCommandService.addReview(customUserDetails.getUser(), reviewRequest, orderDetailId);

        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{reviewId}/reply")
    public BaseResponse<Void> addReviewReply(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid ReviewReplyRequest reviewReplyRequest,
            @PathVariable Long reviewId)
    {
        reviewCommandService.addReviewReply(customUserDetails.getUser(), reviewReplyRequest, reviewId);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{fruitId}/retrieve/all")
    public BaseResponse<Slice<ReviewResponse>> getAllReviews(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size,
            @PathVariable Long fruitId)
    {
        Pageable pageable = PageRequest.of(0, size);

        Slice<ReviewResponse> reviewResponses = reviewQueryService.getAllReviews(cursorId, new ReviewReadCondition(fruitId), pageable);

        return BaseResponse.onSuccess(reviewResponses);
    }

    @GetMapping("/retrieve/my")
    public BaseResponse<Slice<MyReviewResponse>> getMyReviews(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);

        Slice<MyReviewResponse> myReviewResponses = reviewQueryService.getMyReviews(customUserDetails.getUser(), cursorId, pageable);

        return BaseResponse.onSuccess(myReviewResponses);
    }

    @GetMapping("/retrieve/my/{reviewId}")
    public BaseResponse<ReviewResponse> getMyReview(
            @PathVariable Long reviewId)
    {
        ReviewResponse reviewResponse = reviewQueryService.getMyReview(reviewId);

        return BaseResponse.onSuccess(reviewResponse);
    }

    @GetMapping("/seller/{fruitId}/answered")
    public BaseResponse<Slice<ReviewResponse>> getAnsweredSellerReviews(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long fruitId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);

        Slice<ReviewResponse> myReviewResponses = reviewQueryService.getSellerReviews(customUserDetails.getUser(), cursorId, pageable, new ReviewReadCondition(fruitId, true));

        return BaseResponse.onSuccess(myReviewResponses);
    }

    @GetMapping("/seller/{fruitId}/notAnswered")
    public BaseResponse<Slice<ReviewResponse>> getNotAnsweredSellerReviews(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long fruitId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);

        Slice<ReviewResponse> myReviewResponses = reviewQueryService.getSellerReviews(customUserDetails.getUser(), cursorId, pageable, new ReviewReadCondition(fruitId, false));

        return BaseResponse.onSuccess(myReviewResponses);
    }

    @GetMapping("/seller/notAnswered")
    public BaseResponse<Slice<ReviewResponse>> getSellerNotAnsweredReviews(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size)
    {
        Pageable pageable = PageRequest.of(0, size);

        Slice<ReviewResponse> myReviewResponses = reviewQueryService.getSellerReviews(customUserDetails.getUser(), cursorId, pageable, new ReviewReadCondition(false));

        return BaseResponse.onSuccess(myReviewResponses);
    }
}
