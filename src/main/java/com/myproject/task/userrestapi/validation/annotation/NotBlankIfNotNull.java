package com.myproject.task.userrestapi.validation.annotation;

import com.myproject.task.userrestapi.validation.validator.NotBlankIfNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankIfNotNullValidator.class)
public @interface NotBlankIfNotNull {

    String message() default "Field must not be blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
