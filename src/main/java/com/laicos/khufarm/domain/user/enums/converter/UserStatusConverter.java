package com.laicos.khufarm.domain.user.enums.converter;

import com.laicos.khufarm.domain.user.enums.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus){
        return userStatus != null ? userStatus.getCode() : null;
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData){
        if(dbData == null) {
            return null;
        }

        return UserStatus.ofCode(dbData);
    }
}
