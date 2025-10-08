package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutorials.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.TutorialBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTutorial_success() {
        Tutorial validTutorial = new TutorialBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTutorial(validTutorial);

        assertCommandSuccess(new AddCommand(validTutorial), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validTutorial)),
                expectedModel);
    }

    @Test
    public void execute_duplicateTutorial_throwsCommandException() {
        Tutorial tutorialInList = model.getAddressBook().getTutorialList().get(0);
        assertCommandFailure(new AddCommand(tutorialInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
