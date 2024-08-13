package ru.scadouge.ewm.event.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidFutureDateValidator implements ConstraintValidator<ValidFutureDate, Object> {
    private int minimumMinutes;

    @Override
    public void initialize(ValidFutureDate constraintAnnotation) {
        minimumMinutes = constraintAnnotation.minimumMinutes();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, final ConstraintValidatorContext context) {
        try {
            if (value != null) {
                LocalDateTime localDateTime = (LocalDateTime) value;
                return LocalDateTime.now().plusMinutes(minimumMinutes).isBefore(localDateTime);
            } else {
                return true;
            }
        } catch (Exception ignore) {
            // ignore
        }
        return false;
    }
}