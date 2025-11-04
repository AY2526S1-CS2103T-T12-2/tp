package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tabs.model.Model;
import seedu.tabs.model.TAbs;

/**
 * Clears the TAbs.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tutorials have been removed!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTAbs(new TAbs());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
