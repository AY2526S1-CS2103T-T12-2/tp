package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.logic.commands.CommandTestUtil.showTutorialWithTutorialId;
import static seedu.tabs.logic.commands.UnmarkAllCommand.MESSAGE_UNMARK_ALL_SUCCESS;
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

public class UnmarkAllCommandTest {

    private final Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void execute_validKeywordUnfilteredList_success() {
        Tutorial tutorialToUnmarkAll = TypicalTutorials.TUTORIAL_CS1010_C303;
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C303);

        Tutorial unmarkedTutorial = new TutorialBuilder().withId("C303").withModuleCode("CS1010")
                .withDate("2025-01-20").withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();

        String expectedMessage = String.format(MESSAGE_UNMARK_ALL_SUCCESS,
                tutorialToUnmarkAll.getStudents(), unmarkAllCommand.getTutorialId());

        ModelManager expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.setTutorial(tutorialToUnmarkAll, unmarkedTutorial);
        assertCommandSuccess(unmarkAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywordUnfilteredList_throwsCommandException() {
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C102);
        assertCommandFailure(unmarkAllCommand, model,
                String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, PREDICATE_KEYWORD_C102.getKeyword()));
    }

    @Test
    public void execute_validKeywordFilteredList_success() {
        Tutorial tutorialToUnmarkAll = TypicalTutorials.TUTORIAL_CS1010_C303;
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C303);
        showTutorialWithTutorialId(model, tutorialToUnmarkAll.getTutorialId());

        Tutorial unmarkedTutorial = new TutorialBuilder().withId("C303").withModuleCode("CS1010")
                .withDate("2025-01-20").withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();

        String expectedMessage = String.format(MESSAGE_UNMARK_ALL_SUCCESS,
                tutorialToUnmarkAll.getStudents(), unmarkAllCommand.getTutorialId());

        ModelManager expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.setTutorial(tutorialToUnmarkAll, unmarkedTutorial);
        assertCommandSuccess(unmarkAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywordFilteredList_throwsCommandException() {
        showTutorialWithTutorialId(model, TypicalTutorials.TUTORIAL_CS2103T_A101.getTutorialId());

        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C102);
        assertCommandFailure(unmarkAllCommand, model,
                String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, PREDICATE_KEYWORD_C102.getKeyword()));
    }

    @Test
    public void equals() {
        UnmarkAllCommand unmarkAllFirstCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C101);
        UnmarkAllCommand unmarkAllSecondCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C102);

        // same object -> returns true
        assertEquals(unmarkAllFirstCommand, unmarkAllFirstCommand);

        // not an UnmarkAllCommand -> returns false
        ClearCommand clearCommand = new ClearCommand();
        assertNotEquals(unmarkAllFirstCommand, clearCommand);

        // same values -> returns true
        UnmarkAllCommand unmarkAllFirstCommandCopy = new UnmarkAllCommand(PREDICATE_KEYWORD_C101);
        assertEquals(unmarkAllFirstCommand, unmarkAllFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, unmarkAllFirstCommand);

        // null -> returns false
        assertNotEquals(null, unmarkAllFirstCommand);

        // different tutorial -> returns false
        assertNotEquals(unmarkAllFirstCommand, unmarkAllSecondCommand);
    }

    @Test
    public void toStringMethod() {
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand(PREDICATE_KEYWORD_C101);
        String expected = UnmarkAllCommand.class.getCanonicalName() + "{predicate=" + PREDICATE_KEYWORD_C101 + "}";
        assertEquals(expected, unmarkAllCommand.toString());
    }
}
