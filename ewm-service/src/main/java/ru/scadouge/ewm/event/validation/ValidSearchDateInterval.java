package ru.scadouge.ewm.event.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
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