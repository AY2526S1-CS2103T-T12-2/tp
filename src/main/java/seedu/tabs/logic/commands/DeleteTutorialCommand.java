package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import java.util.List;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Deletes a tutorial identified using its displayed tutorial ID from TAbs.
 */
public class DeleteTutorialCommand extends Command {

    public static final String COMMAND_WORD = "delete_tutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the tutorial ID used in the displayed tutorial list.\n"
            + "Parameters: " + TUTORIAL_ID.prefix + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " t/T1";

    public static final String MESSAGE_DELETE_TUTORIAL_SUCCESS =
            "The following tutorial has been successfully deleted: \n%1$s";

    private final TutorialIdMatchesKeywordPredicate predicate;

    public DeleteTutorialCommand(TutorialIdMatchesKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();

        Tutorial tutorialToDelete = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToDelete == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, predicate.getKeyword()));
        }

        model.deleteTutorial(tutorialToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_SUCCESS, Messages.format(tutorialToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTutorialCommand)) {
            return false;
        }

        DeleteTutorialCommand otherDeleteTutorialCommand = (DeleteTutorialCommand) other;
        return predicate.equals(otherDeleteTutorialCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
