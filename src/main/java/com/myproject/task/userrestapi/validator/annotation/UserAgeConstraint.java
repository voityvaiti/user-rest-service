package com.myproject.task.userrestapi.validator.annotation;

import com.myproject.task.userrestapi.validator.UserAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserAgeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAgeConstraint {
    String message() default "Try again when you grow up :)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
