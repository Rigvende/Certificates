package com.epam.esm.error;

/**
 * Class-storage of static messages for error response cases
 * @author Marianna Patrusova
 * @version 1.0
 */
public final class ErrorMessage {

    public final static String ERROR_0 = "Something goes wrong... Contact us: mail@gmail.com";
    public final static String ERROR_400 = "Hmmm. Probably, some errors in your URL or request?";
    public final static String ERROR_404 = "Sorry. Resource not found.";
    public final static String ERROR_500 = "Bug! Oh, no! Contact us: mail@gmail.com";

    public final static String ERROR_400_TAG = "Oops. Tag fields have errors.";
    public final static String ERROR_400_CERTIFICATE = "Oops. Certificate fields have errors.";
    public final static String ERROR_404_TAG = "Oops. Tag not found.";
    public final static String ERROR_404_CERTIFICATE = "Oops. Certificate not found.";
    public final static String ERROR_500_TAG = "Oops. Tag saving failed.";
    public final static String ERROR_500_CERTIFICATE = "Oops. Certificate saving failed.";

    public final static String ALREADY_EXISTS = "Entity already exists in database";
    public final static String NOT_FOUND = "Entity not found in database";

}
