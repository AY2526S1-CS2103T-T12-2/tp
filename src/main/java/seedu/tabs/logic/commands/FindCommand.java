package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.function.Predicate;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;
/**
 * Finds and lists all tutorials in TAbs whose id contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all tutorials whose module codes and/or tutorial IDs "
            + "contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "If both " + PREFIX_MODULE_CODE + " and " + PREFIX_TUTORIAL_ID + " are provided, "
            + "only tutorials matching BOTH criteria are listed (AND condition).\n"
            + "Parameters: \n"
            + "1. Search by one field: " + PREFIX_TUTORIAL_ID + "KEYWORD [MORE_KEYWORDS]... OR "
            + PREFIX_MODULE_CODE + "KEYWORD [MORE_KEYWORDS]...\n"
            + "2. Search by both fields (AND): " + PREFIX_MODULE_CODE + "KEYWORD [...] "
            + PREFIX_TUTORIAL_ID + "KEYWORD [...]\n"
            + "Example 1 (Module only): " + COMMAND_WORD + " " + PREFIX_MODULE_CODE + " CS2103T CS2101\n"
            + "Example 2 (Tutorial ID only): " + COMMAND_WORD + " " + PREFIX_TUTORIAL_ID + " T10 W12\n"
            + "Example 3 (AND condition): " + COMMAND_WORD + " " + PREFIX_MODULE_CODE + " CS2103T "
            + PREFIX_TUTORIAL_ID + " T10";
    private final Predicate<Tutorial> predicate;

    public FindCommand(Predicate<Tutorial> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTutorialList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredTutorialList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
