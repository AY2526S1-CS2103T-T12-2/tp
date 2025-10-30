package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TutorialIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorialId(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "InvalidFormat";
        assertThrows(IllegalArgumentException.class, () -> new TutorialId(invalidId));
    }

    @Test
    public void isValidName() {
        // null id
        assertThrows(NullPointerException.class, () -> TutorialId.isValidTutorialId(null));

        // invalid id
        assertFalse(TutorialId.isValidTutorialId("")); // empty string
        assertFalse(TutorialId.isValidTutorialId(" ")); // spaces only
        assertFalse(TutorialId.isValidTutorialId("^")); // only non-alphanumeric characters
        assertFalse(TutorialId.isValidTutorialId("PETER*")); // contains non-alphanumeric characters
        assertFalse(TutorialId.isValidTutorialId("PETER JACK")); // contains spaces
        assertFalse(TutorialId.isValidTutorialId("12345")); // numbers only, no letter prefix
        assertFalse(TutorialId.isValidTutorialId("C")); // letter only, no numbers
        assertFalse(TutorialId.isValidTutorialId("T")); // letter only, no numbers
        assertFalse(TutorialId.isValidTutorialId("123A")); // numbers before letters
        assertFalse(TutorialId.isValidTutorialId("CT789")); // multiple letters not allowed
        assertFalse(TutorialId.isValidTutorialId("ABC123")); // multiple letters not allowed
        assertFalse(TutorialId.isValidTutorialId("XYZ999")); // multiple letters not allowed
        assertFalse(TutorialId.isValidTutorialId("C123456789")); // more than 8 digits
        assertFalse(TutorialId.isValidTutorialId("T000000000")); // 9 digits, exceeds maximum

        // valid id - follows [A-Z]\d{1,8} pattern (single letter followed by 1-8 digits)
        assertTrue(TutorialId.isValidTutorialId("C123")); // single letter C with numbers
        assertTrue(TutorialId.isValidTutorialId("T456")); // single letter T with numbers
        assertTrue(TutorialId.isValidTutorialId("C1")); // single digit
        assertTrue(TutorialId.isValidTutorialId("T12345")); // multiple digits
        assertTrue(TutorialId.isValidTutorialId("A1")); // minimum valid format
        assertTrue(TutorialId.isValidTutorialId("Z999")); // any single letter works
        assertTrue(TutorialId.isValidTutorialId("a1")); // lowercase input
        assertTrue(TutorialId.isValidTutorialId("z999")); // lowercase input
        assertTrue(TutorialId.isValidTutorialId("C12345678")); // exactly 8 digits (maximum)
        assertTrue(TutorialId.isValidTutorialId("T99999999")); // 8 digits at boundary
    }

    @Test
    public void equals() {
        TutorialId tutorialId = new TutorialId("C123");

        // same values -> returns true
        assertTrue(tutorialId.equals(new TutorialId("C123")));

        // same object -> returns true
        assertTrue(tutorialId.equals(tutorialId));

        // null -> returns false
        assertFalse(tutorialId.equals(null));

        // different types -> returns false
        assertFalse(tutorialId.equals(5.0f));

        // different values -> returns false
        assertFalse(tutorialId.equals(new TutorialId("T456")));

        // different case -> returns true (case insensitive, input is uppercased)
        assertTrue(tutorialId.equals(new TutorialId("c123")));

        // lowercase input gets uppercased -> returns true
        assertTrue(tutorialId.equals(new TutorialId("C123")));
        assertTrue(new TutorialId("t123").equals(new TutorialId("T123")));
    }
}
