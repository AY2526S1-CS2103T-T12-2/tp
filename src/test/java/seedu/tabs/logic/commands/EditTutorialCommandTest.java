package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.tabs.logic.commands.CommandTestUtil.NON_EXISTENT_TUTORIAL_ID;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.EditTutorialCommand.EditTutorialDescriptor;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;
import seedu.tabs.testutil.TutorialBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTutorialCommand.
 */
public class EditTutorialCommandTest {

    private final Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        // target the first tutorial by its current tutorial ID
        Tutorial original = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());

        // completely new values, retains students
        Tutorial editedTutorial = new TutorialBuilder().withStudents(original.getStudents()).build();
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder(editedTutorial).build();

        EditTutorialCommand editTutorialCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(original.getTutorialId().id),
                descriptor);

        String expectedMessage = String.format(EditTutorialCommand.MESSAGE_EDIT_TUTORIAL_SUCCESS,
                seedu.tabs.logic.Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(original, editedTutorial);

        assertCommandSuccess(editTutorialCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        // target the last tutorial by its current tutorial ID
        Tutorial lastTutorial = model.getFilteredTutorialList()
                .get(model.getFilteredTutorialList().size() - 1);

        Tutorial editedTutorial = new TutorialBuilder(lastTutorial)
                .withId(VALID_TUTORIAL_T456)
                .withModuleCode(VALID_MODULE_CODE_MA1521)
                .build();

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withId(VALID_TUTORIAL_T456)
                .withModuleCode(VALID_MODULE_CODE_MA1521)
                .build();

        EditTutorialCommand editCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(lastTutorial.getTutorialId().id),
                descriptor);

        String expectedMessage = String.format(EditTutorialCommand.MESSAGE_EDIT_TUTORIAL_SUCCESS,
                Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(lastTutorial, editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        // empty descriptor (parser usually guards this, but command can still no-op successfully)
        Tutorial target = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTutorialCommand editCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(target.getTutorialId().id),
                new EditTutorialDescriptor());

        String expectedMessage = String.format(EditTutorialCommand.MESSAGE_EDIT_TUTORIAL_SUCCESS,
                Messages.format(target));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        // filter, then edit the visible tutorial using its ID
        Tutorial tutorialInFilteredList = model.getFilteredTutorialList().get(0);

        Tutorial editedTutorial = new TutorialBuilder(tutorialInFilteredList)
                .withId(VALID_TUTORIAL_T456)
                .build();

        EditTutorialCommand editCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(tutorialInFilteredList.getTutorialId().id),
                new EditTutorialDescriptorBuilder().withId(VALID_TUTORIAL_T456).build());

        String expectedMessage = String.format(EditTutorialCommand.MESSAGE_EDIT_TUTORIAL_SUCCESS,
                Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(tutorialInFilteredList, editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTutorial_failure() {
        // Edit the second tutorial to be identical to the first -> duplicate
        Tutorial first = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tutorial second = model.getFilteredTutorialList().get(INDEX_SECOND_PERSON.getZeroBased());

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder(first).build();

        EditTutorialCommand editCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(second.getTutorialId().id),
                descriptor);

        assertCommandFailure(editCommand, model, EditTutorialCommand.MESSAGE_DUPLICATE_TUTORIAL);
    }

    @Test
    public void execute_tutorialIdNotFound_failure() {
        // Predicate targets a non-existent tutorial ID
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withId(VALID_TUTORIAL_T456)
                .build();

        EditTutorialCommand editCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(NON_EXISTENT_TUTORIAL_ID), // assume not present in TypicalTAbs
                descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND);
    }

    @Test
    public void equals() {
        Tutorial first = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tutorial second = model.getFilteredTutorialList().get(INDEX_SECOND_PERSON.getZeroBased());

        EditTutorialDescriptor descForFirst = new EditTutorialDescriptorBuilder()
                .withId(first.getTutorialId().id)
                .withModuleCode(first.getModuleCode().value)
                .withDate(first.getDate().value)
                .build();

        EditTutorialDescriptor descForSecond = new EditTutorialDescriptorBuilder()
                .withId(second.getTutorialId().id)
                .withModuleCode(second.getModuleCode().value)
                .withDate(second.getDate().value)
                .build();

        EditTutorialCommand standardCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(first.getTutorialId().id),
                descForFirst);

        // same values -> returns true
        EditTutorialDescriptor copyDescriptor = new EditTutorialDescriptor(descForFirst);
        EditTutorialCommand commandWithSameValues = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(first.getTutorialId().id),
                copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(new ClearCommand(), standardCommand);

        // different predicate (targets different tutorial) -> returns false
        assertNotEquals(standardCommand, new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(second.getTutorialId().id),
                descForFirst));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(first.getTutorialId().id),
                descForSecond));
    }
}
