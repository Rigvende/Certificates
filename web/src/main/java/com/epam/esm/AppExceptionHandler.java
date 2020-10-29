package com.epam.esm;

import com.epam.esm.error.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import static com.epam.esm.error.ErrorMessage.*;

/**
 * Class for exception handling
 * @author Marianna Patrusova
 * @version 1.0
 */
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value
            = { MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleConflict() {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(40000, ERROR_400));
    }

    @ExceptionHandler(value
            = { NoHandlerFoundException.class})
    public ResponseEntity<Object> handleNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(40400, ERROR_404));
    }

    @ExceptionHandler(value
            = { NullPointerException.class})
    public ResponseEntity<Object> handleNull() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomError(500, ERROR_500));
    }

}
