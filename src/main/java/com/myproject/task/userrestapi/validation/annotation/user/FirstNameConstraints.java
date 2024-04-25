package com.myproject.task.userrestapi.validation.annotation.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@NotBlank(message = "First name is required.")

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface FirstNameConstraints {

    String message() default "Invalid first name.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
