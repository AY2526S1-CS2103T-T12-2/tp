package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "invalid";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCode));
    }

    @Test
    public void isValidModuleCode() {
        // null moduleCode
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid moduleCode
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("91")); // too short
        assertFalse(ModuleCode.isValidModuleCode("moduleCode")); // no numbers
        assertFalse(ModuleCode.isValidModuleCode("C1234")); // only 1 letter prefix
        assertFalse(ModuleCode.isValidModuleCode("ABCD1234")); // too many letters prefix (4 letters)
        assertFalse(ModuleCode.isValidModuleCode("CS123")); // too few digits
        assertFalse(ModuleCode.isValidModuleCode("CS12345")); // too many digits

        // valid moduleCode
        assertTrue(ModuleCode.isValidModuleCode("CS2103")); // 2 letters + 4 digits
        assertTrue(ModuleCode.isValidModuleCode("CS2103T")); // 2 letters + 4 digits + 1 letter
        assertTrue(ModuleCode.isValidModuleCode("cs2103t")); // lowercase (should be converted to uppercase)
        assertTrue(ModuleCode.isValidModuleCode("MA1521")); // 2 letters + 4 digits
        assertTrue(ModuleCode.isValidModuleCode("ST2334")); // 2 letters + 4 digits
        assertTrue(ModuleCode.isValidModuleCode("ENG1000E")); // 3 letters + 4 digits + 1 letter
    }

    @Test
    public void equals() {
        ModuleCode moduleCode = new ModuleCode("CS2103T");

        // same values -> returns true
        assertTrue(moduleCode.equals(new ModuleCode("CS2103T")));

        // same object -> returns true
        assertTrue(moduleCode.equals(moduleCode));

        // null -> returns false
        assertFalse(moduleCode.equals(null));

        // different types -> returns false
        assertFalse(moduleCode.equals(5.0f));

        // different values -> returns false
        assertFalse(moduleCode.equals(new ModuleCode("MA1521")));
    }
}
