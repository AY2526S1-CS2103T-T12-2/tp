package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.logic.commands.CommandTestUtil.showTutorialWithTutorialId;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;
import seedu.tabs.testutil.TutorialBuilder;
import seedu.tabs.testutil.TypicalTutorials;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Tutorial editedTutorial = new TutorialBuilder().build();
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder(editedTutorial).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(model.getFilteredTutorialList().get(0), editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTutorial = Index.fromOneBased(model.getFilteredTutorialList().size());
        Tutorial lastTutorial = model.getFilteredTutorialList().get(indexLastTutorial.getZeroBased());

        TutorialBuilder tutorialInList = new TutorialBuilder(lastTutorial);
        Tutorial editedTutorial = tutorialInList.withId(VALID_TUTORIAL_T456).withModuleCode(VALID_MODULE_CODE_MA1521)
                .withStudents(VALID_STUDENT_A).build();

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withId(VALID_TUTORIAL_T456)
                .withModuleCode(VALID_MODULE_CODE_MA1521).withStudents(VALID_STUDENT_A).build();
        EditCommand editCommand = new EditCommand(indexLastTutorial, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(lastTutorial, editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditTutorialDescriptor());
        Tutorial editedTutorial = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTutorialWithTutorialId(model, new TutorialId("C101"));

        Tutorial tutorialInFilteredList = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tutorial editedTutorial = new TutorialBuilder(tutorialInFilteredList).withId(VALID_TUTORIAL_T456).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTutorialDescriptorBuilder().withId(VALID_TUTORIAL_T456).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(model.getFilteredTutorialList().get(0), editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTutorialUnfilteredList_failure() {
        Tutorial firstTutorial = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder(firstTutorial).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateTutorialFilteredList_failure() {
        showTutorialWithTutorialId(model, new TutorialId("C101"));

        // edit tutorial in filtered list into a duplicate in TAbs
        Tutorial tutorialInList = model.getTAbs().getTutorialList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTutorialDescriptorBuilder(tutorialInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidTutorialIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withId(VALID_TUTORIAL_T456).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_ID);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of TAbs
     */
    @Test
    public void execute_invalidTutorialIndexFilteredList_failure() {
        showTutorialWithTutorialId(model, TypicalTutorials.TUTORIAL_CS2103T_C101.getTutorialId());
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of TAbs list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTAbs().getTutorialList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTutorialDescriptorBuilder().withId(VALID_TUTORIAL_T456).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TUTORIAL_ID);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_C123);

        // same values -> returns true
        EditTutorialDescriptor copyDescriptor = new EditTutorialDescriptor(DESC_C123);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_C123)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_T456)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditTutorialDescriptor editTutorialDescriptor = new EditTutorialDescriptor();
        EditCommand editCommand = new EditCommand(index, editTutorialDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editTutorialDescriptor="
                + editTutorialDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
