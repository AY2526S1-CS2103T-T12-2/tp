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
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new TutorialId(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> TutorialId.isValidName(null));

        // invalid name
        assertFalse(TutorialId.isValidName("")); // empty string
        assertFalse(TutorialId.isValidName(" ")); // spaces only
        assertFalse(TutorialId.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TutorialId.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TutorialId.isValidName("peter jack")); // alphabets only
        assertTrue(TutorialId.isValidName("12345")); // numbers only
        assertTrue(TutorialId.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(TutorialId.isValidName("Capital Tan")); // with capital letters
        assertTrue(TutorialId.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        TutorialId tutorialId = new TutorialId("Valid Name");

        // same values -> returns true
        assertTrue(tutorialId.equals(new TutorialId("Valid Name")));

        // same object -> returns true
        assertTrue(tutorialId.equals(tutorialId));

        // null -> returns false
        assertFalse(tutorialId.equals(null));

        // different types -> returns false
        assertFalse(tutorialId.equals(5.0f));

        // different values -> returns false
        assertFalse(tutorialId.equals(new TutorialId("Other Valid Name")));
    }
}
