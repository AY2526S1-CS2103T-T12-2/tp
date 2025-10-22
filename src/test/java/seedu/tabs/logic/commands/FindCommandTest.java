package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS1010_C303;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2040_C505;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_MA2001_T606;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTAbs(), new UserPrefs());

    @Test
    public void equals() {
        TutorialIdContainsKeywordsPredicate firstPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("first"));
        TutorialIdContainsKeywordsPredicate secondPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different tutorial -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTutorialFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TutorialIdContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTutorialList());
    }

    @Test
    public void execute_moduleCodeKeywords_singleTutorialFound() {
        // Keyword searches specifically for a module code part
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        // Using "CS1010" to match TUTORIAL_CS1010_C303's module code
        TutorialIdContainsKeywordsPredicate predicate = preparePredicate("CS1010");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUTORIAL_CS1010_C303), model.getFilteredTutorialList());
    }

    @Test
    public void execute_tutorialIdKeywords_multipleTutorialsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TutorialIdContainsKeywordsPredicate predicate = preparePredicate("C303 C505 T606");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUTORIAL_CS1010_C303, TUTORIAL_CS2040_C505, TUTORIAL_MA2001_T606),
                model.getFilteredTutorialList());
    }

    @Test
    public void toStringMethod() {
        TutorialIdContainsKeywordsPredicate predicate =
                new TutorialIdContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code TutorialIdContainsKeywordsPredicate}.
     */
    private TutorialIdContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TutorialIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
