package com.myproject.task.userrestapi.validation.annotation;
import com.myproject.task.userrestapi.validation.validator.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {

    String message() default "TO date must be after FROM date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

