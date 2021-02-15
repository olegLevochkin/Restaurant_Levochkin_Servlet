package com.example.restaurant.controller.validators;

public class PasswordValidator extends Validator<String> {
    private SimpleResult FAILED;

    private final Integer minLength;
    private final Integer maxLength;

    public PasswordValidator(Integer min, Integer max, String message) {
        this.minLength = min;
        this.maxLength = max;
        FAILED = new SimpleResult(message, false);
    }

    @Override
    public Result validate(String password) {
        boolean result = password.matches("(.*[0-9]+.*)");
        if (!result) {
            FAILED = new SimpleResult("Password must contain at least one digit", false);
            return FAILED;
        }
        result = password.matches("(.*[A-Z]+.*)");
        if (!result) {
            FAILED = new SimpleResult("Password must contain at least one capital Latin letter", false);
            return FAILED;
        }
        result = password.matches("(.*[a-z]+.*)");
        if (!result) {
            FAILED = new SimpleResult("Password must contain at least one small Latin letterr", false);
            return FAILED;
        }
        if (password.length() < minLength || password.length() > maxLength) {
            FAILED = new SimpleResult("Password must be more than 6 characters", false);
            return FAILED;
        }
        result = password.matches("(?=.*[A-Z])[0-9a-zA-Z*]{6,}");
        if (!result) {
            FAILED = new SimpleResult("Password must contain only Latin characters", false);
            return FAILED;
        }

        return Validator.OK;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
