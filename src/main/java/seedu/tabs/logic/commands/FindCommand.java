package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

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
            + "that partially matches any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "If both " + MODULE_CODE.prefix + " and " + TUTORIAL_ID.prefix + " are provided, "
            + "only tutorials matching BOTH criteria are listed.\n"
            + "Parameters: \n"
            + "1. Search by one field: " + TUTORIAL_ID.prefix + "KEYWORD [MORE_KEYWORDS] OR "
            + MODULE_CODE.prefix + "KEYWORD [MORE_KEYWORDS]\n"
            + "2. Search by two fields: " + MODULE_CODE.prefix + "KEYWORD [MORE_KEYWORDS] "
            + TUTORIAL_ID.prefix + "KEYWORD [MORE_KEYWORDS]\n"
            + "Example 1 (Module Code only): " + COMMAND_WORD + " " + MODULE_CODE.prefix + "CS2103T 2101\n"
            + "Example 2 (Tutorial ID only): " + COMMAND_WORD + " " + TUTORIAL_ID.prefix + "T10 12\n"
            + "Example 3 (Module Code and Tutorial ID): " + COMMAND_WORD + " " + MODULE_CODE.prefix + "CS2103T 2101 "
            + TUTORIAL_ID.prefix + "T10 12";
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
