package com.laicos.khufarm.domain.fruit.validation.validator;

import com.laicos.khufarm.domain.fruit.repository.FruitCategoryRepository;
import com.laicos.khufarm.domain.fruit.validation.anootation.ExistFruitCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FruitCategoryExistValidator implements ConstraintValidator<ExistFruitCategory, Long> {

    private final FruitCategoryRepository fruitCategoryRepository;


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && fruitCategoryRepository.existsById(value);
    }
}
