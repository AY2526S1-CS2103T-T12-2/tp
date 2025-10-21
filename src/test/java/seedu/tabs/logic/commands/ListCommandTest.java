package seedu.tabs.logic.commands;

import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.logic.commands.CommandTestUtil.showTutorialWithTutorialId;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.testutil.TypicalTutorials;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTAbs(), new UserPrefs());
        expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTutorialWithTutorialId(model, TypicalTutorials.TUTORIAL_CS2103T_C101.getTutorialId());
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
