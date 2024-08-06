package ru.scadouge.stats.validation;

import ru.scadouge.stats.dto.TimeUtils;

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
                LocalDateTime rangeStart = LocalDateTime.parse((String) value[0], TimeUtils.DATE_TIME_FORMATTER);
                LocalDateTime rangeEnd = LocalDateTime.parse((String) value[1], TimeUtils.DATE_TIME_FORMATTER);
                return rangeEnd.isAfter(rangeStart);
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}