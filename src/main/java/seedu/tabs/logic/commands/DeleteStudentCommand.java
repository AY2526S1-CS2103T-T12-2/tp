package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Adds a student to a tutorial
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "delete_student";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove a student identified by the given student id "
            + "from the tutorial specified by the given tutorial id. "
            + "Parameters: INDEX (must be a positive integer) "
            + "id/ [STUDENT_ID]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "id/A0123456Z.";
    public static final String MESSAGE_SUCCESS = "Student %1$s removed from tutorial %2$s";

    private final Index index;
    private final Student student;

    /**
     * @param index     of the tutorial in the filtered tutorial list to add the student
     * @param student   of the student to add
     */
    public DeleteStudentCommand(Index index, Student student) {
        requireAllNonNull(index, student);

        this.index = index;
        this.student = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_TUTORIAL_ID, index.getZeroBased()));
        }

        Tutorial tutorial = lastShownList.get(index.getZeroBased());
        Tutorial updatedTutorial = deleteStudentFromTutorial(tutorial, student);

        model.setTutorial(tutorial, updatedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, student, updatedTutorial.getTutorialId()));
    }

    /**
     * Creates and returns a copy of {@code tutorialToEdit} with the given student removed.
     */
    private static Tutorial deleteStudentFromTutorial(Tutorial tutorialToAdd,
                                                 Student student) {
        assert tutorialToAdd != null;

        Set<Student> updatedStudents = new HashSet<>(tutorialToAdd.getStudents());
        updatedStudents.remove(student);

        return new Tutorial(
                tutorialToAdd.getTutorialId(),
                tutorialToAdd.getModuleCode(),
                tutorialToAdd.getDate(),
                tutorialToAdd.getAddress(),
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
        return index.equals(e.index)
                && student.equals(e.student);
    }
}
