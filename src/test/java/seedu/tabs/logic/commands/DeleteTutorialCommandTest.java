package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.logic.commands.CommandTestUtil.showTutorialWithTutorialId;
import static seedu.tabs.testutil.TypicalPredicates.PREDICATE_KEYWORD_C101;
import static seedu.tabs.testutil.TypicalPredicates.PREDICATE_KEYWORD_C102;
import static seedu.tabs.testutil.TypicalPredicates.PREDICATE_KEYWORD_C303;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.testutil.TypicalTutorials;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTutorialCommand}.
 */
public class DeleteTutorialCommandTest {

    private Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void execute_validKeywordUnfilteredList_success() {
        Tutorial tutorialToDelete = TypicalTutorials.TUTORIAL_CS1010_C303;
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C303);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS,
                Messages.format(tutorialToDelete));

        ModelManager expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.deleteTutorial(tutorialToDelete);
        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywordUnfilteredList_throwsCommandException() {
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C102);
        assertCommandFailure(deleteTutorialCommand, model,
                String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, PREDICATE_KEYWORD_C102.getKeyword()));
    }

    @Test
    public void execute_validKeywordFilteredList_success() {
        Tutorial tutorialToDelete = TypicalTutorials.TUTORIAL_CS1010_C303;
        showTutorialWithTutorialId(model, tutorialToDelete.getTutorialId());

        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C303);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS,
                Messages.format(tutorialToDelete));

        Model expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.deleteTutorial(tutorialToDelete);
        showNoTutorial(expectedModel);

        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywordFilteredList_throwsCommandException() {
        showTutorialWithTutorialId(model, TypicalTutorials.TUTORIAL_CS2103T_A101.getTutorialId());

        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C102);
        assertCommandFailure(deleteTutorialCommand, model,
                String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, PREDICATE_KEYWORD_C102.getKeyword()));
    }

    @Test
    public void equals() {
        DeleteTutorialCommand deleteFirstCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C101);
        DeleteTutorialCommand deleteSecondCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C102);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // not a DeleteTutorialCommand -> returns false
        ClearCommand clearCommand = new ClearCommand();
        assertNotEquals(deleteFirstCommand, clearCommand);

        // same values -> returns true
        DeleteTutorialCommand deleteFirstCommandCopy = new DeleteTutorialCommand(PREDICATE_KEYWORD_C101);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different tutorial -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(PREDICATE_KEYWORD_C101);
        String expected = DeleteTutorialCommand.class.getCanonicalName() + "{predicate=" + PREDICATE_KEYWORD_C101 + "}";
        assertEquals(expected, deleteTutorialCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTutorial(Model model) {
        model.updateFilteredTutorialList(p -> false);

        assertTrue(model.getFilteredTutorialList().isEmpty());
    }
}
