package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Makes a copy of an existing tutorial and adds it to TAbs.
 * Copies all students and fields from an existing tutorial with a new tutorial ID and date.
 */
public class CopyTutorialCommand extends Command {
    public static final String COMMAND_WORD = "copy_tutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Copies a tutorial and adds it to TAbs.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_ID + "NEW_TUTORIAL_ID "
            + PREFIX_FROM + "EXISTING_TUTORIAL_ID "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_ID + "C2 "
            + PREFIX_FROM + "C1 "
            + PREFIX_DATE + "2025-01-01";

    public static final String MESSAGE_SUCCESS = "Tutorial %1$s [%2$s] has been copied from %3$s.";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "A tutorial with the TUTORIAL_ID '%1$s' already exists.";
    public static final String MESSAGE_TUTORIAL_NOT_FOUND = "A tutorial with the TUTORIAL_ID '%1$s' does not exist.";

    private final TutorialId newTutorialId;
    private final TutorialId sourceTutorialId;
    private final Date newDate;

    /**
     * Creates a CopyTutorialCommand to copy an existing tutorial.
     *
     * @param newTutorialId The tutorial ID for the new tutorial.
     * @param sourceTutorialId The tutorial ID of the existing tutorial to copy from.
     * @param newDate The date for the new tutorial.
     */
    public CopyTutorialCommand(TutorialId newTutorialId, TutorialId sourceTutorialId, Date newDate) {
        requireNonNull(newTutorialId);
        requireNonNull(sourceTutorialId);
        requireNonNull(newDate);
        this.newTutorialId = newTutorialId;
        this.sourceTutorialId = sourceTutorialId;
        this.newDate = newDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find source tutorial from the list
        Tutorial sourceTutorial = model.getTAbs().getTutorialList().stream()
                .filter(tutorial -> tutorial.getTutorialId().equals(sourceTutorialId))
                .findFirst()
                .orElse(null);

        if (sourceTutorial == null) {
            throw new CommandException(String.format(MESSAGE_TUTORIAL_NOT_FOUND, sourceTutorialId.id));
        }

        // Create a temporary tutorial to check if new ID already exists
        Tutorial tempTutorial = new Tutorial(newTutorialId, sourceTutorial.getModuleCode(),
                newDate, sourceTutorial.getStudents());
        if (model.hasTutorial(tempTutorial)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TUTORIAL, newTutorialId.id));
        }

        // Let model handle the copying
        model.copyTutorial(sourceTutorial, newTutorialId, newDate);

        // Return success message with tutorial ID, module code, and source ID
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                newTutorialId.id,
                sourceTutorial.getModuleCode().value,
                sourceTutorialId.id));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CopyTutorialCommand otherCommand)) {
            return false;
        }

        return newTutorialId.equals(otherCommand.newTutorialId)
                && sourceTutorialId.equals(otherCommand.sourceTutorialId)
                && newDate.equals(otherCommand.newDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("newTutorialId", newTutorialId)
                .add("sourceTutorialId", sourceTutorialId)
                .add("newDate", newDate)
                .toString();
    }
}
