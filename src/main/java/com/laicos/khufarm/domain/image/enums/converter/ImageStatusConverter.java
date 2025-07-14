package com.laicos.khufarm.domain.image.enums.converter;

import com.laicos.khufarm.domain.image.enums.ImageStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ImageStatusConverter implements AttributeConverter<ImageStatus, String> {

    @Override
    public String convertToDatabaseColumn(ImageStatus imageStatusStatus){
        return imageStatusStatus != null ? imageStatusStatus.getCode() : null;
    }

    @Override
    public ImageStatus convertToEntityAttribute(String dbData){
        if(dbData == null) {
            return null;
        }

        return ImageStatus.ofCode(dbData);
    }
}
