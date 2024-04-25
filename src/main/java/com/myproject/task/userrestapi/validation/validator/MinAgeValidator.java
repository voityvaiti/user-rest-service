package com.myproject.task.userrestapi.validation.validator;

import com.myproject.task.userrestapi.validation.annotation.MinAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;


public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    @Value("${user.age.min}")
    private int minAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        LocalDate minDate = LocalDate.now().minusYears(minAge);

        return minDate.isAfter(value);
    }
}
