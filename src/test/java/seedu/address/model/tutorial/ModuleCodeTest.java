package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCode));
    }

    @Test
    public void isValidModuleCode() {
        // null moduleCode number
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid moduleCode numbers
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("91")); // less than 3 numbers
        assertFalse(ModuleCode.isValidModuleCode("moduleCode")); // non-numeric
        assertFalse(ModuleCode.isValidModuleCode("9011p041")); // alphabets within digits
        assertFalse(ModuleCode.isValidModuleCode("9312 1534")); // spaces within digits

        // valid moduleCode numbers
        assertTrue(ModuleCode.isValidModuleCode("911")); // exactly 3 numbers
        assertTrue(ModuleCode.isValidModuleCode("93121534"));
        assertTrue(ModuleCode.isValidModuleCode("124293842033123")); // long moduleCode numbers
    }

    @Test
    public void equals() {
        ModuleCode moduleCode = new ModuleCode("999");

        // same values -> returns true
        assertTrue(moduleCode.equals(new ModuleCode("999")));

        // same object -> returns true
        assertTrue(moduleCode.equals(moduleCode));

        // null -> returns false
        assertFalse(moduleCode.equals(null));

        // different types -> returns false
        assertFalse(moduleCode.equals(5.0f));

        // different values -> returns false
        assertFalse(moduleCode.equals(new ModuleCode("995")));
    }
}
