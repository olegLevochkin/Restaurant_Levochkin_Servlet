package com.example.restaurant.controller.validators;

import java.math.BigDecimal;
import java.util.Objects;

public class DishValidator extends Validator<String>{
    private final SimpleResult FAILED;

    private BigDecimal minValue;
    private BigDecimal maxValue;

    public DishValidator(BigDecimal min, BigDecimal max, String message) {
        this.minValue = min;
        this.maxValue = max;
        FAILED = new SimpleResult(message +
                " [" + minValue + ";" + maxValue + "]!",false);
    }

    @Override
    public Result validate(String in) {

        BigDecimal input = new BigDecimal(in);
        if((input.compareTo(minValue) >= 0 && input.compareTo(maxValue) <= 0) && Objects.nonNull(nextValidator))
            return nextValidator.validate(in);
        else if((input.compareTo(minValue) >= 0 && input.compareTo(maxValue) <= 0))
            return Validator.OK;

        return FAILED;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
