package com.epam.esm;

import com.epam.esm.error.CustomError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import static com.epam.esm.error.ErrorMessage.*;

/**
 * Class for exception handling
 * @author Marianna Patrusova
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleConflict(HttpMessageNotReadableException e) {
        log.error("Global error. Invalid arguments. " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomError(40000, ERROR_400));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleConflict(MethodArgumentTypeMismatchException e) {
        log.error("Global error. Bad request. " + e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomError(40000, ERROR_400));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException e) {
        log.error("Global error. Resource not found. " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CustomError(40400, ERROR_404));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNull(NullPointerException e) {
        log.error("Global error. Null pointer exception. " + e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomError(50000, ERROR_500));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleAll(Throwable e) {
        log.error("Something goes wrong. " + e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new CustomError(0, ERROR_0));
    }

}
