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
 * Adds students to a tutorial
 */
public class AddStudentCommand extends Command {
    public static final String COMMAND_WORD = "add_student";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a student identified by the given student ID "
            + "to the tutorial identified by the given tutorial ID.\n"
            + "Parameters: "
            + PREFIX_STUDENT + "[STUDENT_ID]... "
            + PREFIX_TUTORIAL_ID + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "A1231231Y "
            + PREFIX_STUDENT + "A3213213Y "
            + PREFIX_TUTORIAL_ID + "T123";
    public static final String MESSAGE_SUCCESS = "The following student(s):\n"
            + "\t%1$s\n"
            + "were added to tutorial %2$s.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "The following student(s):\n"
            + "\t%1$s\n"
            + "are already in tutorial %2$s!";

    private final Set<Student> newStudentsList;
    private final TutorialIdMatchesKeywordPredicate predicate;
    private final Set<Student> duplicateStudentList;

    /**
     * @param newStudentsList of the student to add
     * @param predicate       to filter the tutorial list by the provided tutorial_id
     */
    public AddStudentCommand(Set<Student> newStudentsList, TutorialIdMatchesKeywordPredicate predicate) {
        requireAllNonNull(newStudentsList, predicate);

        this.newStudentsList = newStudentsList;
        this.predicate = predicate;
        this.duplicateStudentList = new HashSet<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();

        Tutorial tutorialToAdd = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToAdd == null) {
            throw new CommandException(Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND);
        }

        Tutorial updatedTutorial = addStudentToTutorial(tutorialToAdd, newStudentsList);

        model.setTutorial(tutorialToAdd, updatedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);

        newStudentsList.removeAll(duplicateStudentList);
        String resultMessage = String.format(MESSAGE_SUCCESS, newStudentsList, updatedTutorial.getTutorialId());
        if (!duplicateStudentList.isEmpty()) {
            resultMessage = resultMessage
                    + "\n"
                    + String.format(
                    MESSAGE_DUPLICATE_STUDENT,
                    duplicateStudentList,
                    tutorialToAdd.getTutorialId());
        }
        return new CommandResult(resultMessage);
    }

    /**
     * Creates and returns a {@code Tutorial} with the new student of {@code tutorialToEdit}
     * edited with {@code editTutorialDescriptor}.
     */
    private Tutorial addStudentToTutorial(Tutorial tutorialToAdd,
                                          Set<Student> newStudentsList) throws CommandException {
        assert tutorialToAdd != null;

        // Get current student set and add new students to the set
        Set<Student> currStudents = tutorialToAdd.getStudents();
        Set<Student> updatedStudents = new HashSet<>(currStudents);
        for (Student newStudent : newStudentsList) {
            if (currStudents.contains(newStudent)) {
                //  Keep track of duplicate students
                duplicateStudentList.add(newStudent);
            } else {
                updatedStudents.add(newStudent);
            }
        }

        // If all new students are duplicate students, throw an exception
        if (duplicateStudentList.size() == newStudentsList.size()) {
            throw new CommandException(
                    String.format(
                            MESSAGE_DUPLICATE_STUDENT,
                            newStudentsList,
                            tutorialToAdd.getTutorialId()));
        }

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
        return newStudentsList.equals(e.newStudentsList)
                && predicate.equals(e.predicate);
    }
}
