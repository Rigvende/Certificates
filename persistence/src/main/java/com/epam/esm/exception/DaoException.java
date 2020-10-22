package com.epam.esm.exception;

/**
 * Class for throwing exceptions on the persistence layer
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DaoException extends RuntimeException {

    private static final long serialVersionUID = -5229618385276143143L;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
