package seedu.tabs.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.AppUtil.checkArgument;

/**
 * Represents a tutorial's module code in the TAbs.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should be 2-4 uppercase letters followed by 4 digits and an optional uppercase letter "
            + "(e.g., CS2103T, MA1521, ST2334, GEQ1000)";
    public static final String VALIDATION_REGEX = "[A-Z]{2,4}\\d{4}[A-Z]?";
    public final String value;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        value = moduleCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
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
        if (!(other instanceof ModuleCode)) {
            return false;
        }

        ModuleCode otherModuleCode = (ModuleCode) other;
        return value.equals(otherModuleCode.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
