package com.laicos.khufarm.domain.fruit.validation.validator;

import com.laicos.khufarm.domain.fruit.repository.WholesaleRetailCategoryRepository;
import com.laicos.khufarm.domain.fruit.validation.anootation.ExistWholesaleRetailCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WholesaleRetailCategoryExistValidator implements ConstraintValidator<ExistWholesaleRetailCategory, Long> {

    private final WholesaleRetailCategoryRepository wholesaleRetailCategoryRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && wholesaleRetailCategoryRepository.existsById(value);
    }
}
