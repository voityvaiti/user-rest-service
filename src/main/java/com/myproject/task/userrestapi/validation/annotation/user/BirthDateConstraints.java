package com.myproject.task.userrestapi.validation.annotation.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@NotNull(message = "Birth date is required.")
@Past(message = "Birth date must be in the past.")

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface BirthDateConstraints {

    String message() default "Invalid birth date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
