package seedu.tabs.logic.commands;

import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tabs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.testutil.TutorialBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTutorialCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTAbs(), new UserPrefs());
    }

    @Test
    public void execute_newTutorial_success() {
        Tutorial validTutorial = new TutorialBuilder().build();

        Model expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        expectedModel.addTutorial(validTutorial);

        assertCommandSuccess(new AddTutorialCommand(validTutorial), model,
                String.format(AddTutorialCommand.MESSAGE_SUCCESS, Messages.format(validTutorial)),
                expectedModel);
    }

    @Test
    public void execute_duplicateTutorial_throwsCommandException() {
        Tutorial tutorialInList = model.getTAbs().getTutorialList().get(0);
        assertCommandFailure(new AddTutorialCommand(tutorialInList), model,
                AddTutorialCommand.MESSAGE_DUPLICATE_TUTORIAL);
    }

}
