package com.laicos.khufarm.domain.user.validator.annotation;

import com.laicos.khufarm.domain.user.validator.validator.UserTypeExistValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserTypeExistValidator.class)
public @interface ExistUserType {

    String message() default "존재하지 않는 사용자 유형입니다.";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
