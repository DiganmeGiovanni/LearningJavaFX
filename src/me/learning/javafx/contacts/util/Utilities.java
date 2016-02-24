package me.learning.javafx.contacts.util;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by giovanni on 2/24/16.
 */
public class Utilities {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "dd MMM, yyyy";

    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();


    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link #DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String formatDate(LocalDate date) {
        return (date == null) ? null : DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link #DATE_PATTERN}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        return parse(dateString) != null;
    }
}
