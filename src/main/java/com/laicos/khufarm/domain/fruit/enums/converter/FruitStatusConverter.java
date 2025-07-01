package com.laicos.khufarm.domain.fruit.enums.converter;

import com.laicos.khufarm.domain.fruit.enums.FruitStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FruitStatusConverter implements AttributeConverter<FruitStatus, String> {

    @Override
    public String convertToDatabaseColumn(FruitStatus userStatus){
        return userStatus != null ? userStatus.getCode() : null;
    }

    @Override
    public FruitStatus convertToEntityAttribute(String dbData){
        if(dbData == null) {
            return null;
        }

        return FruitStatus.ofCode(dbData);
    }
}
