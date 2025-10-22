package seedu.tabs.model.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial's ID in the TAbs.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorialId(String)}
 */
public class TutorialId {

    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial IDs should start with 'C' or 'T' followed by numbers (e.g., C123, T456, CT789)";

    public static final String VALIDATION_REGEX = "(C|T|CT)\\d+";

    public final String id;

    /**
     * Constructs a {@code TutorialId}.
     *
     * @param tutorialId A valid tutorial ID.
     */
    public TutorialId(String tutorialId) {
        requireNonNull(tutorialId);
        checkArgument(isValidTutorialId(tutorialId), MESSAGE_CONSTRAINTS);
        this.id = tutorialId;
    }

    /**
     * Returns true if a given string is a valid tutorial ID.
     */
    public static boolean isValidTutorialId(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialId)) {
            return false;
        }

        TutorialId otherTutorialId = (TutorialId) other;
        return id.equals(otherTutorialId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
