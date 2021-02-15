package com.example.restaurant.controller.validators;

public class NameValidator extends Validator<String> {
    private final SimpleResult FAILED;

    private final Integer minLength;
    private final Integer maxLength;

    public NameValidator(Integer min, Integer max, String message) {
        this.minLength = min;
        this.maxLength = max;
        FAILED = new SimpleResult(message, false);
    }

    @Override
    public Result validate(String login) {
        if (login.length() > minLength && login.length() < maxLength
                && login.matches("(?=.*[a-zA-Z])[0-9a-zA-Z*]{6,}")) {

            return Validator.OK;
        }

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
