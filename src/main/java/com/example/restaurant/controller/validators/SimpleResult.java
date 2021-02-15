package com.example.restaurant.controller.validators;

public class SimpleResult implements Result {

    private String message;
    private boolean ok;

    public SimpleResult(String message, boolean ok) {
        this.message = message;
        this.ok = ok;
    }

    @Override
    public boolean isOk() {
        return ok;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
