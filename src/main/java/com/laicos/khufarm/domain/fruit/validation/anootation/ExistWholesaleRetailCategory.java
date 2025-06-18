package com.laicos.khufarm.domain.fruit.validation.anootation;

import com.laicos.khufarm.domain.fruit.validation.validator.WholesaleRetailCategoryExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WholesaleRetailCategoryExistValidator.class)
public @interface ExistWholesaleRetailCategory {

    String message() default "존재하지 않는 도매/소매 카테고리입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
