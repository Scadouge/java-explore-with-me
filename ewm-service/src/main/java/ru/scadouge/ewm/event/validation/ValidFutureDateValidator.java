package ru.scadouge.ewm.event.validation;

import ru.scadouge.ewm.utils.TimeHelper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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
                LocalDateTime eventDateTime = LocalDateTime.parse(value.toString(), TimeHelper.DATE_TIME_FORMATTER);
                return LocalDateTime.now().plusMinutes(minimumMinutes).isBefore(eventDateTime);
            } else {
                return true;
            }
        } catch (Exception ignore) {
            // ignore
        }
        return false;
    }
}