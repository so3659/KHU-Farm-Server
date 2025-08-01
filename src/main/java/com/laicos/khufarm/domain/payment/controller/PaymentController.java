package com.laicos.khufarm.domain.payment.controller;

import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithOrder;
import com.laicos.khufarm.domain.payment.dto.PortoneConfirmDto;
import com.laicos.khufarm.domain.payment.dto.PortoneWebhookDto;
import com.laicos.khufarm.domain.payment.service.PaymentCommandService;
import com.laicos.khufarm.domain.payment.service.PaymentQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payment", description = "결제 관련 API")
public class PaymentController {

    // confirm으로 재고 확인 후 웹훅으로 값 검증하고 콜백 함수를 통해 주문한 카트 내용 삭제

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    @PostMapping("/confirm")
    public ResponseEntity<BaseResponse<String>> confirm(@RequestBody PortoneConfirmDto portoneConfirmDto) {

        paymentCommandService.confirmPayment(portoneConfirmDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.onSuccess(null));
    }

    @PostMapping("/webhook")
    public ResponseEntity<BaseResponse<String>> webhook(@RequestBody PortoneWebhookDto webhookDto) throws IamportResponseException, IOException {

        paymentCommandService.webhookPayment(webhookDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.onSuccess(null));
    }

    @PostMapping("/deleteCartList")
    public BaseResponse<Void> deleteCartList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) List<Long> cartIdList
            ) {

        paymentCommandService.deleteCartList(customUserDetails.getUser(), cartIdList);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping
    public BaseResponse<Slice<FruitResponseWithOrder>> getPaidFruit(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue="5") int size) {

        Pageable pageable = PageRequest.of(0, size);
        Slice<FruitResponseWithOrder> response = paymentQueryService.getPaidFruit(customUserDetails.getUser(), cursorId, pageable);

        return BaseResponse.onSuccess(response);
    }

    @PostMapping("/refund/{orderDetailId}")
    public BaseResponse<Void> refundPayment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long orderDetailId) throws IamportResponseException, IOException {

        paymentCommandService.refundPayment(customUserDetails.getUser(), orderDetailId);

        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/refund/{orderDetailId}/deny")
    public BaseResponse<Void> refundPaymentDeny(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long orderDetailId){

        paymentCommandService.refundPaymentDeny(customUserDetails.getUser(), orderDetailId);

        return BaseResponse.onSuccess(null);
    }
}
