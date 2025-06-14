package com.laicos.khufarm.domain.user.enums.converter;

import com.laicos.khufarm.domain.user.enums.UserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType){
        return userType != null ? userType.getCode() : null;
    }

    @Override
    public UserType convertToEntityAttribute(String dbData){
        if(dbData == null) {
            return null;
        }

        return UserType.ofCode(dbData);
    }
}

