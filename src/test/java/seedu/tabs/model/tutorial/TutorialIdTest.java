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
        assertFalse(TutorialId.isValidTutorialId("peter*")); // contains non-alphanumeric characters
        assertFalse(TutorialId.isValidTutorialId("peter jack")); // doesn't match required pattern
        assertFalse(TutorialId.isValidTutorialId("12345")); // numbers only, no prefix
        assertFalse(TutorialId.isValidTutorialId("ABC123")); // wrong prefix
        assertFalse(TutorialId.isValidTutorialId("C")); // no numbers
        assertFalse(TutorialId.isValidTutorialId("T")); // no numbers

        // valid id - follows [CT]\d+ pattern
        assertTrue(TutorialId.isValidTutorialId("C123")); // C prefix with numbers
        assertTrue(TutorialId.isValidTutorialId("T456")); // T prefix with numbers
        assertTrue(TutorialId.isValidTutorialId("CT789")); // CT prefix with numbers
        assertTrue(TutorialId.isValidTutorialId("C1")); // single digit
        assertTrue(TutorialId.isValidTutorialId("T12345")); // multiple digits
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
    }
}
