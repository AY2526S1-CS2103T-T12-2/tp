package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_ADDRESS;
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
 * Adds a tutorial to the TAbs using the new tutorial specification.
 */
public class AddTutorialCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial to the TAbs. "
            + "Parameters: "
            + PREFIX_TUTORIAL_ID + "TUTORIAL_ID "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_STUDENT + "STUDENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_ID + "T123 "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_DATE + "2025-03-15 "
            + PREFIX_ADDRESS + "COM1-B103 "
            + PREFIX_STUDENT + "A1231231Y "
            + PREFIX_STUDENT + "A3213213Y";

    public static final String MESSAGE_SUCCESS = "New tutorial added: %1$s";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in TAbs";

    private final Tutorial toAdd;

    /**
     * Creates an AddTutorialCommand to add the specified {@code Tutorial}
     */
    public AddTutorialCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        toAdd = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTutorial(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        model.addTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTutorialCommand)) {
            return false;
        }

        AddTutorialCommand otherAddTutorialCommand = (AddTutorialCommand) other;
        return toAdd.equals(otherAddTutorialCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

