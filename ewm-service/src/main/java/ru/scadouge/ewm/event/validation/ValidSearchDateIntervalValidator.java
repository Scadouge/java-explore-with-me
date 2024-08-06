package ru.scadouge.ewm.event.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidSearchDateIntervalValidator implements ConstraintValidator<ValidSearchDateInterval, Object[]> {
    @Override
    public boolean isValid(Object[] value, final ConstraintValidatorContext context) {
        try {
            if (value[0] != null && value[1] != null) {
                LocalDateTime rangeStart = (LocalDateTime) value[0];
                LocalDateTime rangeEnd = (LocalDateTime) value[1];
                return rangeEnd.isAfter(rangeStart);
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}