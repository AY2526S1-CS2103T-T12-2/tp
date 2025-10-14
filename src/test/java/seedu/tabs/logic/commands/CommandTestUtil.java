package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
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

    public static final String VALID_NAME_AMY = "C123";
    public static final String VALID_NAME_BOB = "T456";
    public static final String VALID_PHONE_AMY = "CS2103T";
    public static final String VALID_PHONE_BOB = "MA1521";
    public static final String VALID_DATE_AMY = "2025-01-15";
    public static final String VALID_DATE_BOB = "2025-02-20";
    public static final String VALID_TAG_HUSBAND = "A1231231Y";
    public static final String VALID_TAG_FRIEND = "A3213213Y";

    public static final String NAME_DESC_AMY = " " + PREFIX_TUTORIAL_ID + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_TUTORIAL_ID + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_MODULE_CODE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_MODULE_CODE + VALID_PHONE_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_STUDENT + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_STUDENT + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_TUTORIAL_ID + "James&"; // '&' not allowed
    public static final String INVALID_PHONE_DESC = " " + PREFIX_MODULE_CODE + "911a"; // 'a' not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "bob!yahoo"; // invalid date format
    public static final String INVALID_TAG_DESC = " " + PREFIX_STUDENT + "hubby*"; // '*' not allowed in students

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTutorialDescriptor DESC_AMY;
    public static final EditCommand.EditTutorialDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditTutorialDescriptorBuilder().withName(VALID_NAME_AMY)
                .withModuleCode(VALID_PHONE_AMY).withDate(VALID_DATE_AMY)
                .withStudents(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditTutorialDescriptorBuilder().withName(VALID_NAME_BOB)
                .withModuleCode(VALID_PHONE_BOB).withDate(VALID_DATE_BOB)
                .withStudents(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
