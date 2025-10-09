package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidEmail));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // invalid date formats
        assertFalse(Date.isValidDate("2025/01/15")); // wrong separator
        assertFalse(Date.isValidDate("24-01-15")); // wrong year format
        assertFalse(Date.isValidDate("2025-1-15")); // missing leading zero in month
        assertFalse(Date.isValidDate("2025-01-5")); // missing leading zero in day
        assertFalse(Date.isValidDate("15-01-2025")); // wrong order
        assertFalse(Date.isValidDate("abcd-01-15")); // non-numeric year
        assertFalse(Date.isValidDate("2025-ab-15")); // non-numeric month
        assertFalse(Date.isValidDate("2025-01-ab")); // non-numeric day

        // valid dates
        assertTrue(Date.isValidDate("2025-01-15")); // standard format
        assertTrue(Date.isValidDate("2023-12-31")); // end of year
        assertTrue(Date.isValidDate("2024-02-29")); // leap year (2024 is a leap year)
        assertTrue(Date.isValidDate("2000-01-01")); // start of millennium
        assertTrue(Date.isValidDate("1999-06-15")); // past date
        assertTrue(Date.isValidDate("2030-08-22")); // future date
    }

    @Test
    public void equals() {
        Date date = new Date("2025-01-15");

        // same values -> returns true
        assertTrue(date.equals(new Date("2025-01-15")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("2025-02-20")));
    }
}
