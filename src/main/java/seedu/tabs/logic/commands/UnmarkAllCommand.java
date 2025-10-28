package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import java.util.List;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Unmarks all the students in a {@code Tutorial}.
 */
public class UnmarkAllCommand extends Command {

    public static final String COMMAND_WORD = "unmark_all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks all the students in the tutorial identified by the tutorial ID.\n"
            + "Parameters: " + PREFIX_TUTORIAL_ID + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " t/T1";

    public static final String MESSAGE_UNMARK_ALL_SUCCESS = "The following student(s):\n"
            + "\t%1$s\n"
            + "were unmarked in tutorial %2$s.";

    private final TutorialIdMatchesKeywordPredicate predicate;

    public UnmarkAllCommand(TutorialIdMatchesKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();

        Tutorial tutorialToUnmarkAll = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToUnmarkAll == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, predicate.getKeyword()));
        }

        tutorialToUnmarkAll.unmarkAllStudents();
        model.setTutorial(tutorialToUnmarkAll, tutorialToUnmarkAll);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);

        return new CommandResult(String.format(MESSAGE_UNMARK_ALL_SUCCESS,
                tutorialToUnmarkAll.getStudents(), predicate.getKeyword()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkAllCommand)) {
            return false;
        }

        UnmarkAllCommand otherUnmarkAllCommand = (UnmarkAllCommand) other;
        return predicate.equals(otherUnmarkAllCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
