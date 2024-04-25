package com.myproject.task.userrestapi.validation.validator;

import com.myproject.task.userrestapi.validation.annotation.AgeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {

    @Value("${user.age.min}")
    private int minAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(minAge);

        return minDate.isAfter(value);
    }
}
