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
public class AddStudentCommand extends Command {
    public static final String COMMAND_WORD = "add_student";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a student identified by the given student ID "
            + "to the index number of the tutorial in the last tutorial listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "id/ [STUDENT_ID]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "id/A0123456Z.";
    public static final String MESSAGE_SUCCESS = "New student %1$s added to tutorial %2$s";

    private final Index index;
    private final Student student;

    /**
     * @param index     of the tutorial in the filtered tutorial list to add the student
     * @param student   of the student to add
     */
    public AddStudentCommand(Index index, Student student) {
        requireAllNonNull(index, student);

        this.index = index;
        this.student = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Tutorial tutorialToAdd = lastShownList.get(index.getZeroBased());
        Tutorial updatedTutorial = addStudentToTutorial(tutorialToAdd, student);

        model.setTutorial(tutorialToAdd, updatedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, student, updatedTutorial.getTutorialId()));
    }

    /**
     * Creates and returns a {@code Tutorial} with the new student of {@code tutorialToEdit}
     * edited with {@code editTutorialDescriptor}.
     */
    private static Tutorial addStudentToTutorial(Tutorial tutorialToAdd,
                                                 Student student) {
        assert tutorialToAdd != null;

        Set<Student> updatedStudents = new HashSet<>(tutorialToAdd.getStudents());
        updatedStudents.add(student);

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
        if (!(other instanceof AddStudentCommand)) {
            return false;
        }

        AddStudentCommand e = (AddStudentCommand) other;
        return index.equals(e.index)
                && student.equals(e.student);
    }
}
