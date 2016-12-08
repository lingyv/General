package org.lingyv.validator.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BetweenValidator implements ConstraintValidator<Between, Integer> {

    private int min;
    private int max;

    public void initialize(Between annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value >= min && value <= max) {
            return true;
        }
        return false;
    }

}
