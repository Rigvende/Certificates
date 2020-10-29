package com.epam.esm;

import com.epam.esm.error.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
            = { NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleConflict(NoHandlerFoundException ex) {
            ex.printStackTrace();
            return ResponseEntity.ok(new CustomError(40400, ERROR_404));
    }

}
