package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Adds a tutorial to the TAbs.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial to the TAbs. "
            + "Parameters: "
            + PREFIX_TUTORIAL_ID + "TUTORIAL_ID "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_STUDENT + "STUDENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_ID + "C123 "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_DATE + "2025-01-15 "
            + PREFIX_STUDENT + "friends "
            + PREFIX_STUDENT + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New tutorial added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This tutorial already exists in the TAbs";

    private final Tutorial toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Tutorial}
     */
    public AddCommand(Tutorial aTutorial) {
        requireNonNull(aTutorial);
        toAdd = aTutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTutorial(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
