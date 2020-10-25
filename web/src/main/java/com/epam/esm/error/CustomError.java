package com.epam.esm.error;

public class CustomError {

    private final int code;
    private final String message;

    public CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
