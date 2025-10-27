package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import seedu.tabs.model.Model;

/**
 * Lists all tutorials in the TAbs to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tutorials.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
