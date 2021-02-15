package com.example.restaurant.controller.validators;

import java.util.Objects;

public class RangeLengthValidator  extends Validator<String>{
    private final SimpleResult FAILED;

    private Integer minLength;
    private Integer maxLength;

    public RangeLengthValidator(Integer min, Integer max, String message) {
        this.minLength = min;
        this.maxLength = max;
        FAILED = new SimpleResult(message +
                " [" + minLength + ";" + maxLength + "]!",false);
    }

    public RangeLengthValidator(Integer min, Integer max, Validator<String> next, String message) {
        super(next);
        this.minLength = min;
        this.maxLength = max;
        FAILED = new SimpleResult(message +
                " [" + minLength + ";" + maxLength + "]!",false);
    }

    @Override
    public Result validate(String input) {
        if((input.length() >= minLength && input.length() <= maxLength) && Objects.nonNull(nextValidator))
            return nextValidator.validate(input);
        else if((input.length() >= minLength && input.length() <= maxLength))
            return Validator.OK;

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
