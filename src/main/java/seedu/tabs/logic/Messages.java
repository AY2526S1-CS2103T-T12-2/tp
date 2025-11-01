package seedu.tabs.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.tabs.logic.parser.Prefix;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TUTORIAL_ID = "A tutorial with tutorial ID '%s' does not exist.";
    public static final String MESSAGE_TUTORIAL_ID_NOT_FOUND = "No tutorial with the provided tutorial ID was found.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d tutorial(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EXTRA_PREFIXES =
                "Unexpected prefix(es) found: ";
    public static final String MESSAGES_STUDENTS_LISTED_OVERVIEW = "Displaying all students enrolled in tutorial!";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating the extra prefixes.
     */
    public static String getErrorMessageForExtraPrefixes(Prefix... extraPrefixes) {
        assert extraPrefixes.length > 0;

        Set<String> extraFields =
                Stream.of(extraPrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_EXTRA_PREFIXES + String.join(" ", extraFields);
    }

    /**
     * Formats the {@code tutorial} for display to the user.
     */
    public static String format(Tutorial aTutorial) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Tutorial ID: ")
                .append(aTutorial.getTutorialId())
                .append("\nModule Code: ")
                .append(aTutorial.getModuleCode())
                .append("\nDate: ")
                .append(aTutorial.getDate())
                .append("\nStudents: ")
                .append(aTutorial.getStudentsAsString());

        return builder.toString();
    }

}
