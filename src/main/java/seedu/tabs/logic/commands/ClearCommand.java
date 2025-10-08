package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tabs.model.TAbs;
import seedu.tabs.model.Model;

/**
 * Clears the TAbs.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTAbs(new TAbs());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
