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
 * Marks all the students in a {@code Tutorial} as present.
 */
public class MarkAllCommand extends Command {

    public static final String COMMAND_WORD = "mark_all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark all the students in the tutorial identified by the tutorial ID as present.\n"
            + "Parameters: " + PREFIX_TUTORIAL_ID + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " t/T1";

    public static final String MESSAGE_MARK_ALL_SUCCESS = "The following student(s):\n"
            + "\t%1$s\n"
            + "were marked as present in tutorial %2$s.";

    public final TutorialIdMatchesKeywordPredicate predicate;

    public MarkAllCommand(TutorialIdMatchesKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Returns the tutorial ID of the predicate of the {@code MarkAllCommand}.
     */
    public String getTutorialId() {
        return predicate.getKeyword();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();

        Tutorial tutorialToMarkAll = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToMarkAll == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, predicate.getKeyword()));
        }

        tutorialToMarkAll.markAllStudents();
        model.setTutorial(tutorialToMarkAll, tutorialToMarkAll);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);

        return new CommandResult(String.format(MESSAGE_MARK_ALL_SUCCESS,
                tutorialToMarkAll.getStudents(), predicate.getKeyword()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAllCommand)) {
            return false;
        }

        MarkAllCommand otherMarkAllCommand = (MarkAllCommand) other;
        return predicate.equals(otherMarkAllCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
