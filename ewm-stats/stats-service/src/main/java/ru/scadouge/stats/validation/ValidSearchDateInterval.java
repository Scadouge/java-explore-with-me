package ru.scadouge.stats.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {ValidSearchDateIntervalValidator.class}
)
public @interface ValidSearchDateInterval {
    String message() default "Date interval validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}