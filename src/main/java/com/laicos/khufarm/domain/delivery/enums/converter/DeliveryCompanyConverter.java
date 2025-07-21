package com.laicos.khufarm.domain.delivery.enums.converter;

import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DeliveryCompanyConverter implements AttributeConverter<DeliveryCompany, String> {

    @Override
    public String convertToDatabaseColumn(DeliveryCompany deliveryCompany) {
        return deliveryCompany != null ? deliveryCompany.getName() : null;
    }

    @Override
    public DeliveryCompany convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return DeliveryCompany.fromName(dbData);
    }
}
