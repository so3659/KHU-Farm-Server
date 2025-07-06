package com.laicos.khufarm.domain.payment.enums.converter;

import com.laicos.khufarm.domain.payment.enums.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentStatus paymentStatus) {
        return paymentStatus != null ? paymentStatus.getCode() : null;
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return PaymentStatus.ofCode(dbData);
    }
}