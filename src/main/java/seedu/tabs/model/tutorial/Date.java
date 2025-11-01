package seedu.tabs.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Tutorial's date in the TAbs.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in YYYY-MM-DD format (e.g., 2025-03-15) and represent a valid calendar date"
            + "with year between 1998 and 2200";

    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    private static final int MIN_YEAR = 1998;
    private static final int MAX_YEAR = 2200;

    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date in YYYY-MM-DD format.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test == null || !test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            LocalDate date = LocalDate.parse(test);
            int year = date.getYear();
            return year >= MIN_YEAR && year <= MAX_YEAR;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
