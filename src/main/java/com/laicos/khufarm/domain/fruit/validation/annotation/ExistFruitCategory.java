package com.laicos.khufarm.domain.fruit.validation.annotation;

import com.laicos.khufarm.domain.fruit.validation.validator.FruitCategoryExistValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FruitCategoryExistValidator.class)
public @interface ExistFruitCategory {

    String message() default "존재하지 않는 과일 카테고리입니다.";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
