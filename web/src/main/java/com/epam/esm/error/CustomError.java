package com.epam.esm.error;

import com.epam.esm.util.ErrorField;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

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
    private final List<ErrorField> fields;

}
