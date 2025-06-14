package com.laicos.khufarm.domain.point.enums.converter;

import com.laicos.khufarm.domain.point.enums.PointEventType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PointEventTypeConverter implements AttributeConverter<PointEventType, String> {

    @Override
    public String convertToDatabaseColumn(PointEventType pointEventType) {
        return pointEventType != null ? pointEventType.getCode() : null;
    }

    @Override
    public PointEventType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return PointEventType.ofCode(dbData);
    }
}
