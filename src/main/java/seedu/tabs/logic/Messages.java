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
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d tutorials listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
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
     * Formats the {@code tutorial} for display to the user.
     */
    public static String format(Tutorial aTutorial) {
        final StringBuilder builder = new StringBuilder();
        builder.append("TutorialID: ")
                .append(aTutorial.getTutorialId())
                .append("; ModuleCode: ")
                .append(aTutorial.getModuleCode())
                .append("; Date: ")
                .append(aTutorial.getDate())
                .append("; Address: ")
                .append(aTutorial.getAddress())
                .append("; Students: ");
        aTutorial.getStudents().forEach(builder::append);
        return builder.toString();
    }

}
