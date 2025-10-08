package seedu.tabs.logic.commands;

import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.Test;

import seedu.tabs.model.TAbs;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTAbs_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTAbs_success() {
        Model model = new ModelManager(getTypicalTAbs(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTAbs(), new UserPrefs());
        expectedModel.setTAbs(new TAbs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
