package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Lists all students in a class in the TAbs to the user.
 */
public class ListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "list_students";

    // dummy values
    private String listOfStudents = "1. A0303333Z \n2. A0303334Z \n3. A0303335Z \n4. A0303336Z";

    private final TutorialId tutorialId;

    public ListStudentsCommand(TutorialId tutorialId) {
        this.tutorialId = tutorialId;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(listOfStudents);
    }
}
