package com.laicos.khufarm.domain.termsAgreement.enums.converter;

import com.laicos.khufarm.domain.termsAgreement.enums.TermsEssential;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TermsEssentialConverter implements AttributeConverter<TermsEssential, String> {

    @Override
    public String convertToDatabaseColumn(TermsEssential termsEssential) {
        return termsEssential != null ? termsEssential.getCode() : null;
    }

    @Override
    public TermsEssential convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TermsEssential.ofCode(dbData);
    }
}
