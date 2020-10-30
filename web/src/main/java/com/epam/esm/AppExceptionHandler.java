package com.epam.esm;

import com.epam.esm.error.CustomError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.io.IOException;

import static com.epam.esm.error.ErrorMessage.*;

/**
 * Class for exception handling
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleConflict() {
        log.error("Global error. Bad request.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomError(40000, ERROR_400));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound() {
        log.error("Global error. Resource not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CustomError(40400, ERROR_404));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNull() {
        log.error("Global error. Null pointer exception.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomError(50000, ERROR_500));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleAll() {
        log.error("Something goes wrong...");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new CustomError(0, ERROR_0));
    }

}
