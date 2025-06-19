package com.laicos.khufarm.domain.seller.validation.validator;

import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import com.laicos.khufarm.domain.seller.validation.annotation.ExistSeller;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerExistValidator implements ConstraintValidator<ExistSeller, Long> {

    private final SellerRepository sellerRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && sellerRepository.existsById(value);
    }
}
