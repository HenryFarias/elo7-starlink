package br.com.elo7.starlink.mock;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;

public class ConstraintValidatorContextMock implements ConstraintValidatorContext {

    @Override
    public void disableDefaultConstraintViolation() {

    }

    @Override
    public String getDefaultConstraintMessageTemplate() {
        return null;
    }

    @Override
    public ClockProvider getClockProvider() {
        return null;
    }

    @Override
    public ConstraintViolationBuilder buildConstraintViolationWithTemplate(String s) {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
