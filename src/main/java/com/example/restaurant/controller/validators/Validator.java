package com.example.restaurant.controller.validators;

public abstract class Validator<T> {

    Validator<T> nextValidator;

    Validator(Validator<T> nextValidator) {
        this.nextValidator = nextValidator;
    }

    Validator() {
    }

    public abstract Result validate(T value);

    static final Result OK = new Result() {
        @Override
        public String getMessage() {
            return "OK";
        }

        @Override
        public String toString() {
            return "OK";
        }

        @Override
        public boolean isOk() {
            return true;
        }
    };

}
