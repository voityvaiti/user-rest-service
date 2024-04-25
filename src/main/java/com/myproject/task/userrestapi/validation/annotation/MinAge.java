package com.myproject.task.userrestapi.validation.annotation;

import com.myproject.task.userrestapi.validation.validator.MinAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = MinAgeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {

    String message() default "Too young.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
