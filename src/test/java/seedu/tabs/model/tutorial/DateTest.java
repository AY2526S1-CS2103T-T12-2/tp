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
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void constructor_invalidCalendarDate_throwsIllegalArgumentException() {
        // Test that constructor properly rejects invalid calendar dates
        assertThrows(IllegalArgumentException.class, () -> new Date("2025-13-01")); // invalid month
        assertThrows(IllegalArgumentException.class, () -> new Date("2025-02-30")); // invalid day for February
        assertThrows(IllegalArgumentException.class, () -> new Date("2025-04-31")); // invalid day for April
        assertThrows(IllegalArgumentException.class, () -> new Date("2025-02-29")); // invalid for non-leap year
    }

    @Test
    public void isValidDate() {
        // null date
        assertFalse(Date.isValidDate(null));

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

        // invalid calendar dates (correct format but impossible dates)
        assertFalse(Date.isValidDate("2025-13-01")); // month 13 doesn't exist
        assertFalse(Date.isValidDate("2025-00-15")); // month 0 doesn't exist
        assertFalse(Date.isValidDate("2025-01-00")); // day 0 doesn't exist
        assertFalse(Date.isValidDate("2025-01-32")); // January doesn't have 32 days
        assertFalse(Date.isValidDate("2025-02-30")); // February doesn't have 30 days
        assertFalse(Date.isValidDate("2025-04-31")); // April doesn't have 31 days
        assertFalse(Date.isValidDate("2025-06-31")); // June doesn't have 31 days
        assertFalse(Date.isValidDate("2025-09-31")); // September doesn't have 31 days
        assertFalse(Date.isValidDate("2025-11-31")); // November doesn't have 31 days
        assertFalse(Date.isValidDate("2025-02-29")); // 2025 is not a leap year
        assertFalse(Date.isValidDate("2023-02-29")); // 2023 is not a leap year
        assertFalse(Date.isValidDate("1900-02-29")); // 1900 is not a leap year (century rule)

        // valid dates
        assertTrue(Date.isValidDate("2025-01-15")); // standard format
        assertTrue(Date.isValidDate("2023-12-31")); // end of year
        assertTrue(Date.isValidDate("2024-02-29")); // leap year (2024 is a leap year)
        assertTrue(Date.isValidDate("2000-02-29")); // leap year (2000 is a leap year)
        assertTrue(Date.isValidDate("2000-01-01")); // start of millennium
        assertTrue(Date.isValidDate("1999-06-15")); // past date
        assertTrue(Date.isValidDate("2030-08-22")); // future date
        assertTrue(Date.isValidDate("2025-01-31")); // January 31st
        assertTrue(Date.isValidDate("2025-03-31")); // March 31st
        assertTrue(Date.isValidDate("2025-05-31")); // May 31st
        assertTrue(Date.isValidDate("2025-07-31")); // July 31st
        assertTrue(Date.isValidDate("2025-08-31")); // August 31st
        assertTrue(Date.isValidDate("2025-10-31")); // October 31st
        assertTrue(Date.isValidDate("2025-12-31")); // December 31st
        assertTrue(Date.isValidDate("2025-04-30")); // April 30th
        assertTrue(Date.isValidDate("2025-06-30")); // June 30th
        assertTrue(Date.isValidDate("2025-09-30")); // September 30th
        assertTrue(Date.isValidDate("2025-11-30")); // November 30th
        assertTrue(Date.isValidDate("2025-02-28")); // February 28th (non-leap year)
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
