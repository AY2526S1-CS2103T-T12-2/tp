package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.logic.commands.CommandTestUtil.showTutorialWithTutorialId;
import static seedu.tabs.logic.commands.MarkAllCommand.MESSAGE_MARK_ALL_SUCCESS;
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
import seedu.tabs.testutil.TutorialBuilder;
import seedu.tabs.testutil.TypicalTutorials;

public class MarkAllCommandTest {

    private final Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void execute_validKeywordUnfilteredList_success() {
        Tutorial tutorialToMarkAll = TypicalTutorials.TUTORIAL_CS1010_C303;
        MarkAllCommand markAllCommand = new MarkAllCommand(PREDICATE_KEYWORD_C303);

        Tutorial markedTutorial = new TutorialBuilder().withId("C303").withModuleCode("CS1010")
                .withDate("2025-01-20").withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();

        String expectedMessage = String.format(MESSAGE_MARK_ALL_SUCCESS,
                tutorialToMarkAll.getStudents(), markAllCommand.getTutorialId());

        ModelManager expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.setTutorial(tutorialToMarkAll, markedTutorial);
        assertCommandSuccess(markAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywordUnfilteredList_throwsCommandException() {
        MarkAllCommand markAllCommand = new MarkAllCommand(PREDICATE_KEYWORD_C102);
        assertCommandFailure(markAllCommand, model,
                String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, PREDICATE_KEYWORD_C102.getKeyword()));
    }

    @Test
    public void execute_validKeywordFilteredList_success() {
        Tutorial tutorialToMarkAll = TypicalTutorials.TUTORIAL_CS1010_C303;
        MarkAllCommand markAllCommand = new MarkAllCommand(PREDICATE_KEYWORD_C303);
        showTutorialWithTutorialId(model, tutorialToMarkAll.getTutorialId());

        Tutorial markedTutorial = new TutorialBuilder().withId("C303").withModuleCode("CS1010")
                .withDate("2025-01-20").withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();

        String expectedMessage = String.format(MESSAGE_MARK_ALL_SUCCESS,
                tutorialToMarkAll.getStudents(), markAllCommand.getTutorialId());

        ModelManager expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.setTutorial(tutorialToMarkAll, markedTutorial);
        assertCommandSuccess(markAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywordFilteredList_throwsCommandException() {
        showTutorialWithTutorialId(model, TypicalTutorials.TUTORIAL_CS2103T_C101.getTutorialId());

        MarkAllCommand markAllCommand = new MarkAllCommand(PREDICATE_KEYWORD_C102);
        assertCommandFailure(markAllCommand, model,
                String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, PREDICATE_KEYWORD_C102.getKeyword()));
    }

    @Test
    public void equals() {
        MarkAllCommand markAllFirstCommand = new MarkAllCommand(PREDICATE_KEYWORD_C101);
        MarkAllCommand markAllSecondCommand = new MarkAllCommand(PREDICATE_KEYWORD_C102);

        // same object -> returns true
        assertEquals(markAllFirstCommand, markAllFirstCommand);

        // not a MarkAllCommand -> returns false
        ClearCommand clearCommand = new ClearCommand();
        assertNotEquals(markAllFirstCommand, clearCommand);

        // same values -> returns true
        MarkAllCommand markAllFirstCommandCopy = new MarkAllCommand(PREDICATE_KEYWORD_C101);
        assertEquals(markAllFirstCommand, markAllFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, markAllFirstCommand);

        // null -> returns false
        assertNotEquals(null, markAllFirstCommand);

        // different tutorial -> returns false
        assertNotEquals(markAllFirstCommand, markAllSecondCommand);
    }

    @Test
    public void toStringMethod() {
        MarkAllCommand markAllCommand = new MarkAllCommand(PREDICATE_KEYWORD_C101);
        String expected = MarkAllCommand.class.getCanonicalName() + "{predicate=" + PREDICATE_KEYWORD_C101 + "}";
        assertEquals(expected, markAllCommand.toString());
    }
}
