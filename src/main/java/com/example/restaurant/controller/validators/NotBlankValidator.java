package com.example.restaurant.controller.validators;

import java.util.Objects;

public class NotBlankValidator extends Validator<String> {

    private final SimpleResult FAILED;

    public NotBlankValidator(String message) {
        FAILED = new SimpleResult(message, false);
    }

    public NotBlankValidator(Validator<String> next, String message) {
        super(next);
        FAILED = new SimpleResult(message, false);
    }

    @Override
    public Result validate(String input) {
        if((input.length() > 0) && Objects.nonNull(nextValidator))
            return nextValidator.validate(input);
        else if((input.length() > 0))
            return Validator.OK;

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
