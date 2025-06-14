package com.laicos.khufarm.domain.order.enums.converter;

import com.laicos.khufarm.domain.order.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus != null ? orderStatus.getCode() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OrderStatus.ofCode(dbData);
    }
}
