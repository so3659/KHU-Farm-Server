package com.laicos.khufarm.domain.user.validator.validator;

import com.laicos.khufarm.domain.user.validator.annotation.ExistUserType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTypeExistValidator implements ConstraintValidator<ExistUserType, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // value가 null이 아니고, userType이 존재하는지 확인
        return value != null && (value == 1 || value == 2 || value == 3);
    }
}
