package com.laicos.khufarm.domain.seller.validation.annotation;

import com.laicos.khufarm.domain.seller.validation.validator.SellerExistValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SellerExistValidator.class)
public @interface ExistSeller {

    String message() default "존재하지 않는 판매자입니다.";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
