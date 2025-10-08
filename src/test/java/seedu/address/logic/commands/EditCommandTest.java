package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTutorialAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.address.model.TAbs;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.EditTutorialDescriptorBuilder;
import seedu.address.testutil.TutorialBuilder;

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

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(model.getFilteredTutorialList().get(0), editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTutorial = Index.fromOneBased(model.getFilteredTutorialList().size());
        Tutorial lastTutorial = model.getFilteredTutorialList().get(indexLastTutorial.getZeroBased());

        TutorialBuilder tutorialInList = new TutorialBuilder(lastTutorial);
        Tutorial editedTutorial = tutorialInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastTutorial, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());
        expectedModel.setTutorial(lastTutorial, editedTutorial);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditTutorialDescriptor());
        Tutorial editedTutorial = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedTutorial));

        Model expectedModel = new ModelManager(new TAbs(model.getTAbs()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTutorialAtIndex(model, INDEX_FIRST_PERSON);

        Tutorial tutorialInFilteredList = model.getFilteredTutorialList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tutorial editedTutorial = new TutorialBuilder(tutorialInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTutorialDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedTutorial));

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
        showTutorialAtIndex(model, INDEX_FIRST_PERSON);

        // edit tutorial in filtered list into a duplicate in address book
        Tutorial tutorialInList = model.getTAbs().getTutorialList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditTutorialDescriptorBuilder(tutorialInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidTutorialIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTutorialIndexFilteredList_failure() {
        showTutorialAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTAbs().getTutorialList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTutorialDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTutorialDescriptor copyDescriptor = new EditTutorialDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
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
