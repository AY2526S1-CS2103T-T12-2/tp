package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.logic.commands.CommandTestUtil.showTutorialAtIndex;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.Messages;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTutorialCommandTest {

    private Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Tutorial tutorialToDelete = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(tutorialToDelete));

        ModelManager expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.deleteTutorial(tutorialToDelete);

        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(outOfBoundIndex);

        assertCommandFailure(deleteTutorialCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTutorialAtIndex(model, INDEX_FIRST_PERSON);

        Tutorial tutorialToDelete = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTutorialCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(tutorialToDelete));

        Model expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.deleteTutorial(tutorialToDelete);
        showNoTutorial(expectedModel);

        assertCommandSuccess(deleteTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTutorialAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of TAbs list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTAbs().getTutorialList().size());

        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(outOfBoundIndex);

        assertCommandFailure(deleteTutorialCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTutorialCommand deleteFirstCommand = new DeleteTutorialCommand(INDEX_FIRST_PERSON);
        DeleteTutorialCommand deleteSecondCommand = new DeleteTutorialCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTutorialCommand deleteFirstCommandCopy = new DeleteTutorialCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tutorial -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTutorialCommand deleteTutorialCommand = new DeleteTutorialCommand(targetIndex);
        String expected = DeleteTutorialCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
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
