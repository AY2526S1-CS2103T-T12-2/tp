package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Adds a tutorial to the TAbs using the new tutorial specification.
 */
public class AddTutorialCommand extends Command {

    public static final String COMMAND_WORD = "add_tutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial to TAbs.\n"
            + "Parameters: "
            + TUTORIAL_ID.prefix + "TUTORIAL_ID "
            + MODULE_CODE.prefix + "MODULE_CODE "
            + DATE.prefix + "DATE "
            + "[" + STUDENT.prefix + "STUDENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + TUTORIAL_ID.prefix + "T123 "
            + MODULE_CODE.prefix + "CS2103T "
            + DATE.prefix + "2025-03-15 "
            + STUDENT.prefix + "A1231231Y "
            + STUDENT.prefix + "A3213213Y";

    public static final String MESSAGE_SUCCESS = "A new tutorial has been added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in TAbs.";

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
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
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

