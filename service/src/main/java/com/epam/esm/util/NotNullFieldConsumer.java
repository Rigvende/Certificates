package com.epam.esm.util;

import java.util.function.Consumer;

/**
 * Class for null checking and consuming field value
 * @author Marianna Patrusova
 * @version 1.0
 */
public final class NotNullFieldConsumer {

    /**
     * Method: check field for being not null, if true - consumer takes value of field
     * @param checkingField: any field type
     * @param consumerField: {@link Consumer} object parametrized by checkingField type
     */
    public static <T> void changeIfPresent(T checkingField, Consumer<T> consumerField) {
        if (checkingField != null) {
            consumerField.accept(checkingField);
        }

    }

}
