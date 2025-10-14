package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * Adds a student to a tutorial
 */
public class AddStudentCommand extends Command {
    public static final String COMMAND_WORD = "add_student";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a student identified by the given student ID "
            + "to the tutorial identified by the given tutorial ID.\n"
            + "Parameters: "
            + PREFIX_STUDENT + "[STUDENT_ID] "
            + PREFIX_TUTORIAL_ID + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "A0123456Z "
            + PREFIX_TUTORIAL_ID + "T123";
    public static final String MESSAGE_SUCCESS = "New student %1$s added to tutorial %2$s";

    private final Student student;
    private final TutorialIdMatchesKeywordPredicate predicate;

    /**
     * @param student   of the student to add
     * @param predicate to filter the tutorial list by the provided tutorial_id
     */
    public AddStudentCommand(Student student, TutorialIdMatchesKeywordPredicate predicate) {
        requireAllNonNull(student, predicate);

        this.student = student;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        Tutorial tutorialToAdd = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToAdd == null) {
            throw new CommandException(Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND);
        }

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
        return student.equals(e.student)
                && predicate.equals(e.predicate);
    }
}
