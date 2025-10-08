package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Deletes a tutorial identified using it's displayed index from the TAbs.
 */
public class DeleteTutorialCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tutorial identified by the index number used in the displayed tutorial list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Tutorial: %1$s";

    private final Index targetIndex;

    public DeleteTutorialCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Tutorial tutorialToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTutorial(tutorialToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(tutorialToDelete)));
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
        return targetIndex.equals(otherDeleteTutorialCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
