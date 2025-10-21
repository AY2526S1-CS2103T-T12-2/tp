package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TUTORIAL_C123 = "C123";
    public static final String VALID_TUTORIAL_T456 = "T456";
    public static final String VALID_TUTORIAL_C2 = "C2";
    public static final String NON_EXISTENT_TUTORIAL_ID = "Z999";
    public static final String VALID_MODULE_CODE_CS2103T = "CS2103T";
    public static final String VALID_MODULE_CODE_MA1521 = "MA1521";
    public static final String VALID_DATE_C123 = "2025-01-15";
    public static final String VALID_DATE_T456 = "2025-02-20";
    public static final String VALID_DATE_C2 = "2025-03-10";
    public static final String VALID_STUDENT_A = "A1231231Y";
    public static final String VALID_STUDENT_B = "A3213213Y";

    public static final String TUTORIAL_DESC_C123 = " " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_C123;
    public static final String TUTORIAL_DESC_T456 = " " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_T456;
    public static final String TUTORIAL_DESC_C2 = " " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_C2;
    public static final String FROM_DESC_C123 = " " + PREFIX_FROM + VALID_TUTORIAL_C123;
    public static final String FROM_DESC_T456 = " " + PREFIX_FROM + VALID_TUTORIAL_T456;
    public static final String FROM_DESC_NONEXISTENT = " " + PREFIX_FROM + NON_EXISTENT_TUTORIAL_ID;
    public static final String MODULE_CODE_DESC_CS2103T = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS2103T;
    public static final String MODULE_CODE_DESC_MA1521 = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_MA1521;
    public static final String DATE_DESC_C123 = " " + PREFIX_DATE + VALID_DATE_C123;
    public static final String DATE_DESC_T456 = " " + PREFIX_DATE + VALID_DATE_T456;
    public static final String DATE_DESC_C2 = " " + PREFIX_DATE + VALID_DATE_C2;
    public static final String STUDENT_DESC_A = " " + PREFIX_STUDENT + VALID_STUDENT_A;
    public static final String STUDENT_DESC_B = " " + PREFIX_STUDENT + VALID_STUDENT_B;

    public static final String INVALID_TUTORIAL_DESC = " " + PREFIX_TUTORIAL_ID + "James&"; // '&' not allowed
    public static final String INVALID_FROM_DESC = " " + PREFIX_FROM + "Invalid&"; // '&' not allowed
    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + "911a"; // 'a' not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "bob!yahoo"; // invalid date format
    public static final String INVALID_STUDENT_DESC = " " + PREFIX_STUDENT + "hubby*"; // '*' not allowed in students

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTutorialDescriptor DESC_C123;
    public static final EditCommand.EditTutorialDescriptor DESC_T456;

    static {
        DESC_C123 = new EditTutorialDescriptorBuilder().withName(VALID_TUTORIAL_C123)
                .withModuleCode(VALID_MODULE_CODE_CS2103T).withDate(VALID_DATE_C123)
                .withStudents(VALID_STUDENT_B).build();
        DESC_T456 = new EditTutorialDescriptorBuilder().withName(VALID_TUTORIAL_T456)
                .withModuleCode(VALID_MODULE_CODE_MA1521).withDate(VALID_DATE_T456)
                .withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the TAbs, filtered tutorial list and selected tutorial in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TAbs expectedTAbs = new TAbs(actualModel.getTAbs());
        List<Tutorial> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTutorialList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTAbs, actualModel.getTAbs());
        assertEquals(expectedFilteredList, actualModel.getFilteredTutorialList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the tutorial at the given {@code targetIndex} in the
     * {@code model}'s TAbs.
     */
    public static void showTutorialAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTutorialList().size());

        Tutorial aTutorial = model.getFilteredTutorialList().get(targetIndex.getZeroBased());
        final String[] splitName = aTutorial.getTutorialId().fullName.split("\\s+");
        model.updateFilteredTutorialList(new TutorialIdContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTutorialList().size());
    }

}
