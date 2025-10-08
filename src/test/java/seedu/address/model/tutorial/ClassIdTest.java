package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClassIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassId(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ClassId(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> ClassId.isValidName(null));

        // invalid name
        assertFalse(ClassId.isValidName("")); // empty string
        assertFalse(ClassId.isValidName(" ")); // spaces only
        assertFalse(ClassId.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ClassId.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ClassId.isValidName("peter jack")); // alphabets only
        assertTrue(ClassId.isValidName("12345")); // numbers only
        assertTrue(ClassId.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(ClassId.isValidName("Capital Tan")); // with capital letters
        assertTrue(ClassId.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        ClassId classId = new ClassId("Valid Name");

        // same values -> returns true
        assertTrue(classId.equals(new ClassId("Valid Name")));

        // same object -> returns true
        assertTrue(classId.equals(classId));

        // null -> returns false
        assertFalse(classId.equals(null));

        // different types -> returns false
        assertFalse(classId.equals(5.0f));

        // different values -> returns false
        assertFalse(classId.equals(new ClassId("Other Valid Name")));
    }
}
