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
    public void isValidEmail() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidEmail(null));

        // blank date
        assertFalse(Date.isValidEmail("")); // empty string
        assertFalse(Date.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidEmail("@example.com")); // missing local part
        assertFalse(Date.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Date.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Date.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Date.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Date.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Date.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Date.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Date.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Date.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Date.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Date.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(Date.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(Date.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(Date.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Date.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Date.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Date.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Date.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Date.isValidEmail("peterjack@example.c")); // top level domain has less than two chars

        // valid date
        assertTrue(Date.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Date.isValidEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(Date.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Date.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Date.isValidEmail("a@bc")); // minimal
        assertTrue(Date.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Date.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Date.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Date.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Date.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Date.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        Date date = new Date("valid@date");

        // same values -> returns true
        assertTrue(date.equals(new Date("valid@date")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("other.valid@date")));
    }
}
