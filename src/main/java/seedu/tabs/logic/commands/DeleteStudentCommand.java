package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Edits a student to a tutorial
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "delete_student";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete a student identified by the given student ID "
            + "from the tutorial identified by the given tutorial ID.\n"
            + "Parameters: "
            + PREFIX_STUDENT + "[STUDENT_ID] "
            + PREFIX_TUTORIAL_ID + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "A0123456Z "
            + PREFIX_TUTORIAL_ID + "T123";
    public static final String MESSAGE_SUCCESS = "Student %1$s deleted from tutorial %2$s.";
    public static final String MESSAGE_NOT_EXISTS = "Student %1$s is not in tutorial %2$s.";

    private final TutorialIdMatchesKeywordPredicate predicate;
    private final Student student;

    /**
     * @param index of the tutorial in the filtered tutorial list to add the student
     * @param student of the student to add
     */
    public DeleteStudentCommand(TutorialIdMatchesKeywordPredicate predicate, Student student) {
        requireAllNonNull(predicate, student);

        this.predicate = predicate;
        this.student = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();
        Tutorial tutorialToChange = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToChange == null) {
            throw new CommandException(Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND);
        }

        Tutorial updatedTutorial = deleteStudentFromTutorial(tutorialToChange, student);

        model.setTutorial(tutorialToChange, updatedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);

        String resultMessage = String.format(MESSAGE_SUCCESS, student.studentId, updatedTutorial.getTutorialId());

        return new CommandResult(resultMessage);
    }

    /**
     * Creates and returns a copy of {@code tutorialToEdit} with the given student removed.
     */
    private Tutorial deleteStudentFromTutorial(Tutorial tutorialToEdit,
                                               Student student) throws CommandException {
        assert tutorialToEdit != null;

        Set<Student> updatedStudents = new HashSet<>(tutorialToEdit.getStudents());

        //If the size of set did not change after .remove(), the student does not exist.
        if (!updatedStudents.remove(student)) {
            throw new CommandException(
                    String.format(MESSAGE_NOT_EXISTS, student.studentId, tutorialToEdit.getTutorialId()));
        }

        return new Tutorial(
                tutorialToEdit.getTutorialId(),
                tutorialToEdit.getModuleCode(),
                tutorialToEdit.getDate(),
                updatedStudents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand e = (DeleteStudentCommand) other;
        return predicate.equals(e.predicate)
                && student.equals(e.student);
    }
}
