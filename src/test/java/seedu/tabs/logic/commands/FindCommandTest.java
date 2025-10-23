package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS1010_C303;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2040_C505;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_C101;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_MA2001_T606;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.ModuleCodeContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTAbs(), new UserPrefs());

    // --- Utility Methods for Predicate Creation ---

    /**
     * Parses {@code userInput} into a {@code TutorialIdContainsKeywordsPredicate}.
     */
    private TutorialIdContainsKeywordsPredicate prepareTutorialIdPredicate(String userInput) {
        return new TutorialIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code ModuleCodeContainsKeywordsPredicate}.
     */
    private ModuleCodeContainsKeywordsPredicate prepareModuleCodePredicate(String userInput) {
        return new ModuleCodeContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    // --- Test Cases ---

    @Test
    public void equals() {
        TutorialIdContainsKeywordsPredicate idPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("C01"));
        ModuleCodeContainsKeywordsPredicate modulePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS1010"));

        // Define two FindCommands with different types of predicates for comprehensive testing
        FindCommand findByIdCommand = new FindCommand(idPredicate);
        FindCommand findByModuleCommand = new FindCommand(modulePredicate);

        // same object -> returns true
        assertTrue(findByIdCommand.equals(findByIdCommand));

        // same values -> returns true
        FindCommand findByIdCommandCopy = new FindCommand(idPredicate);
        assertTrue(findByIdCommand.equals(findByIdCommandCopy));

        // different types -> returns false
        assertFalse(findByIdCommand.equals(1));

        // null -> returns false
        assertFalse(findByIdCommand.equals(null));

        // different predicate types -> returns false (Crucial fix)
        assertFalse(findByIdCommand.equals(findByModuleCommand));

        // different keywords (same predicate type) -> returns false
        TutorialIdContainsKeywordsPredicate differentIdPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("C02"));
        FindCommand findByDifferentIdCommand = new FindCommand(differentIdPredicate);
        assertFalse(findByIdCommand.equals(findByDifferentIdCommand));
    }

    @Test
    public void execute_zeroKeywords_noTutorialFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        // Use an ID predicate that searches for a non-existent keyword
        TutorialIdContainsKeywordsPredicate predicate = prepareTutorialIdPredicate("nonexistentkeyword");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTutorialList());
    }

    @Test
    public void execute_moduleCodeKeywords_singleTutorialFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        // Use ModuleCodeContainsKeywordsPredicate (Crucial fix)
        ModuleCodeContainsKeywordsPredicate predicate = prepareModuleCodePredicate("CS1010");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUTORIAL_CS1010_C303), model.getFilteredTutorialList());
    }

    @Test
    public void execute_tutorialIdKeywords_multipleTutorialsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        // Use TutorialIdContainsKeywordsPredicate and search for IDs (Crucial fix)
        TutorialIdContainsKeywordsPredicate predicate = prepareTutorialIdPredicate("C303 C505 T606");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUTORIAL_CS1010_C303, TUTORIAL_CS2040_C505, TUTORIAL_MA2001_T606),
                model.getFilteredTutorialList());
    }

    @Test
    public void execute_moduleCodeKeywords_multipleTutorialsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        // Search for "CS" which should match both CS1010_C303 and CS2040_C505
        ModuleCodeContainsKeywordsPredicate predicate = prepareModuleCodePredicate("CS");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTutorialList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUTORIAL_CS2103T_C101, TUTORIAL_CS1010_C303, TUTORIAL_CS2040_C505),
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
}
