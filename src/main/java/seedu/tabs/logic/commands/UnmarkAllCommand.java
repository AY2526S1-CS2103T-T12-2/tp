package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

public class UnmarkAllCommand extends Command {

    public static final String COMMAND_WORD = "unmark_all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the tutorial ID used in the displayed tutorial list.\n"
            + "Example: " + COMMAND_WORD + " t/T1";

    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS = "Tutorial deleted: %1$s";

    private final TutorialIdMatchesKeywordPredicate predicate;

    public UnmarkAllCommand(TutorialIdMatchesKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();

        Tutorial tutorialToMarkAll = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToMarkAll == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, predicate.getKeyword()));
        }

        // model.deleteTutorial(tutorialToMarkAll);
        return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, Messages.format(tutorialToMarkAll)));
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
