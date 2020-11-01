package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class as object of a field containing error
 * @author Marianna Patrusova
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class ErrorField {

    private String field;
    private String message;

}
