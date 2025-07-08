package com.laicos.khufarm.domain.payment.service;

import com.laicos.khufarm.domain.payment.dto.PortoneConfirmDto;
import com.laicos.khufarm.domain.payment.dto.PortoneWebhookDto;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface PaymentCommandService {

    void confirmPayment(PortoneConfirmDto portoneConfirmDto);
    void webhookPayment(PortoneWebhookDto webhookDto) throws IamportResponseException, IOException;
}
