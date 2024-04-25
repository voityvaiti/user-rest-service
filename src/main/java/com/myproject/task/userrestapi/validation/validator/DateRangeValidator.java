package com.myproject.task.userrestapi.validation.validator;

import com.myproject.task.userrestapi.dto.request.DateRangeReqDto;
import com.myproject.task.userrestapi.validation.annotation.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    @Override
    public void initialize(DateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value instanceof DateRangeReqDto dateRange) {
            return dateRange.getFrom().isBefore(dateRange.getTo());
        }
        return true;
    }
}

