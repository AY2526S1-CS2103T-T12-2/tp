package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tabs.logic.parser.CliSyntax.STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;
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
 * Unmarks as present, students in a given tutorial
 */
public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "unmark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks a student identified by the given student ID "
            + "in the tutorial identified by the given tutorial ID.\n"
            + "Parameters: "
            + STUDENT.prefix + "[STUDENT_ID]... "
            + TUTORIAL_ID.prefix + "[TUTORIAL_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + TUTORIAL_ID.prefix + "T123 "
            + STUDENT.prefix + "A1231231Y "
            + STUDENT.prefix + "A3213213Y";
    public static final String MESSAGE_SUCCESS = "The following student(s):\n"
            + "\t%1$s\n"
            + "were unmarked in tutorial %2$s.\n";
    public static final String MESSAGE_ALREADY_MARKED = "The following student(s):\n"
            + "\t%1$s\n"
            + "were already unmarked in tutorial %2$s.\n";
    public static final String MESSAGE_NOT_EXISTS = "The following student(s):\n"
            + "\t%1$s\n"
            + "are not in tutorial %2$s.\n";

    private final Set<Student> newStudentsList;
    private final Set<Student> successfullyUnmarkedStudents;
    private final Set<Student> alreadyUnmarkedStudents;
    private final Set<Student> nonExistentStudents;
    private final TutorialIdMatchesKeywordPredicate predicate;

    /**
     * @param newStudentsList of the student to unmark
     * @param predicate       to filter the tutorial list by the provided tutorial_id
     */
    public UnmarkCommand(Set<Student> newStudentsList, TutorialIdMatchesKeywordPredicate predicate) {
        requireAllNonNull(newStudentsList, predicate);

        this.newStudentsList = newStudentsList;
        this.successfullyUnmarkedStudents = new HashSet<>();
        this.alreadyUnmarkedStudents = new HashSet<>();
        this.nonExistentStudents = new HashSet<>(newStudentsList);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();

        Tutorial tutorial = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorial == null) {
            throw new CommandException(Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND);
        }

        Tutorial updatedTutorial = unmarkStudents(tutorial, newStudentsList);
        model.setTutorial(tutorial, updatedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);

        String resultMessage = "";

        if (!successfullyUnmarkedStudents.isEmpty()) {
            resultMessage += String.format(MESSAGE_SUCCESS, successfullyUnmarkedStudents,
                    updatedTutorial.getTutorialId());
        }

        if (!alreadyUnmarkedStudents.isEmpty()) {
            resultMessage += String.format(MESSAGE_ALREADY_MARKED, alreadyUnmarkedStudents,
                    updatedTutorial.getTutorialId());
        }

        if (!nonExistentStudents.isEmpty()) {
            resultMessage += String.format(MESSAGE_NOT_EXISTS, nonExistentStudents,
                    updatedTutorial.getTutorialId());
        }

        if (successfullyUnmarkedStudents.isEmpty()) {
            throw new CommandException(resultMessage);
        }

        return new CommandResult(resultMessage);
    }

    /**
     * Creates and returns a {@code Tutorial} with the newly unmarked students of {@code tutorialToEdit}
     */
    private Tutorial unmarkStudents(Tutorial tutorial,
                                    Set<Student> studentsToUnmark) {
        assert tutorial != null;

        Set<Student> currStudents = tutorial.getStudents();
        for (Student student : currStudents) {
            if (studentsToUnmark.contains(student)) {
                if (!student.getAttendance()) {
                    alreadyUnmarkedStudents.add(student);
                } else {
                    student.unmark();
                    successfullyUnmarkedStudents.add(student);
                }
                nonExistentStudents.remove(student);
            }
        }

        return new Tutorial(
                tutorial.getTutorialId(),
                tutorial.getModuleCode(),
                tutorial.getDate(),
                currStudents);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand e)) {
            return false;
        }

        return newStudentsList.equals(e.newStudentsList)
                && predicate.equals(e.predicate);
    }
}
