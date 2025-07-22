package com.laicos.khufarm.domain.payment.service;

import com.laicos.khufarm.domain.payment.dto.PortoneConfirmDto;
import com.laicos.khufarm.domain.payment.dto.PortoneWebhookDto;
import com.laicos.khufarm.domain.user.entity.User;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;
import java.util.List;

public interface PaymentCommandService {

    void confirmPayment(PortoneConfirmDto portoneConfirmDto);
    void webhookPayment(PortoneWebhookDto webhookDto) throws IamportResponseException, IOException;
    void deleteCartList(User user, List<Long> cartIdList);
    void refundPayment(User user, Long orderDetailId) throws IamportResponseException, IOException;
}
