package com.laicos.khufarm.domain.payment.converter;

import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.payment.entity.Payment;
import com.laicos.khufarm.domain.payment.enums.PaymentStatus;
import com.siot.IamportRestClient.response.IamportResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {

    public static Payment toPayment(Order order, IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse){
        return Payment.builder()
                .totalPrice(iamportResponse.getResponse().getAmount().intValue())
                .impUid(iamportResponse.getResponse().getImpUid())
                .merchantUid(iamportResponse.getResponse().getMerchantUid())
                .payMethod(iamportResponse.getResponse().getPayMethod())
                .paymentStatus(PaymentStatus.PAYMENT_COMPLETED)
                .paidAt(iamportResponse.getResponse().getPaidAt())
                .cancelledAt(iamportResponse.getResponse().getCancelledAt())
                .failedAt(iamportResponse.getResponse().getFailedAt())
                .failReason(iamportResponse.getResponse().getFailReason())
                .order(order)
                .user(order.getUser())
                .build();
    }
}
