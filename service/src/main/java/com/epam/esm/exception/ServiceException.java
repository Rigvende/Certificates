package com.epam.esm.exception;

public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1683198621034566090L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
