package com.myproject.task.userrestapi.validation.validator;

import com.myproject.task.userrestapi.validation.annotation.NotBlankIfNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class NotBlankIfNotNullValidator implements ConstraintValidator<NotBlankIfNotNull, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        return !value.trim().isEmpty();
    }

}
