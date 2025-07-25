package com.laicos.khufarm.domain.inquiry.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.inquiry.dto.InquiryReadCondition;
import com.laicos.khufarm.domain.inquiry.dto.request.InquiryReplyRequest;
import com.laicos.khufarm.domain.inquiry.dto.request.InquiryRequest;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.MyInquiryResponse;
import com.laicos.khufarm.domain.inquiry.service.InquiryCommandService;
import com.laicos.khufarm.domain.inquiry.service.InquiryQueryService;
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
@RequestMapping("/inquiry")
@RequiredArgsConstructor
@Validated
@Tag(name = "Inquiry", description = "문의 관련 API")
public class InquiryController {

    private final InquiryCommandService inquiryCommandService;
    private final InquiryQueryService inquiryQueryService;

    @GetMapping("/{fruitId}")
    public BaseResponse<Slice<InquiryResponse>> getAllInquiry(
            @PathVariable Long fruitId,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<InquiryResponse> inquiries = inquiryQueryService.getAllInquiry(cursorId, new InquiryReadCondition(fruitId), pageable);
        return BaseResponse.onSuccess(inquiries);
    }

    @GetMapping("/myInquiry")
    public BaseResponse<Slice<InquiryResponse>> getMyAllInquiry(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<InquiryResponse> inquiries = inquiryQueryService.getAllInquiry(cursorId, new InquiryReadCondition(customUserDetails.getUser()), pageable);
        return BaseResponse.onSuccess(inquiries);
    }

    @GetMapping("/myInquiry/{inquiryId}")
    public BaseResponse<MyInquiryResponse> getMyAllInquiry(
            @PathVariable Long inquiryId) {

        MyInquiryResponse myInquiryResponse = inquiryQueryService.getMyInquiry(inquiryId);
        return BaseResponse.onSuccess(myInquiryResponse);
    }

    @PostMapping("/{fruitId}/add")
    public BaseResponse<Void> addInquiry(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid InquiryRequest inquiryRequest,
            @PathVariable Long fruitId)
    {
        inquiryCommandService.addInquiry(customUserDetails.getUser(), inquiryRequest, fruitId);

        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/{inquiryId}/reply")
    public BaseResponse<Void> addReviewReply(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid InquiryReplyRequest inquiryReplyRequest,
            @PathVariable Long inquiryId)
    {
        inquiryCommandService.addInquiryReply(customUserDetails.getUser(), inquiryReplyRequest, inquiryId);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/seller/all")
    public BaseResponse<Slice<InquiryResponse>> getSellerAllInquiry(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<InquiryResponse> inquiries = inquiryQueryService.getSellerInquiry(cursorId, customUserDetails.getUser(), pageable, new InquiryReadCondition());
        return BaseResponse.onSuccess(inquiries);
    }

    @GetMapping("/seller/notAnswered")
    public BaseResponse<Slice<InquiryResponse>> getSellerNotAnsweredInquiry(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<InquiryResponse> inquiries = inquiryQueryService.getSellerInquiry(cursorId, customUserDetails.getUser(), pageable, new InquiryReadCondition(false));
        return BaseResponse.onSuccess(inquiries);
    }
}
