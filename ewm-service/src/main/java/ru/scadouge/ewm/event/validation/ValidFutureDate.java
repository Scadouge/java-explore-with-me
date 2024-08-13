package ru.scadouge.ewm.event.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {ValidFutureDateValidator.class}
)
public @interface ValidFutureDate {
    String message() default "Future date validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minimumMinutes() default 1;
}