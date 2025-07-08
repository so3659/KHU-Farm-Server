package com.laicos.khufarm.domain.payment.controller;

import com.laicos.khufarm.domain.payment.dto.PortoneConfirmDto;
import com.laicos.khufarm.domain.payment.dto.PortoneWebhookDto;
import com.laicos.khufarm.domain.payment.service.PaymentCommandService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payment", description = "결제 관련 API")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;

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
}
