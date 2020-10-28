package com.epam.esm.error;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class as error response object
 * @author Marianna Patrusova
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class CustomError {

    private final int code;
    private final String message;

}
