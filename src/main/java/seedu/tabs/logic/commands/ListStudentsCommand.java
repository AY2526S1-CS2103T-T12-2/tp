package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import java.util.ArrayList;
import java.util.List;

import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Lists all students in a class in the TAbs to the user.
 */
public class ListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "list_students";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the students in a tutorial\n"
            + "Parameters: "
            + TUTORIAL_ID.prefix + "TUTORIAL_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + TUTORIAL_ID.prefix + "T123";

    private static final String MESSAGE_SUCCESS_TEMPLATE = "Displaying all students enrolled in tutorial %s:\n";


    // dummy values
    private String listOfStudents = "";

    private final TutorialId tutorialId;


    public ListStudentsCommand(TutorialId tutorialId) {
        this.tutorialId = tutorialId;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException If a tutorial with the given ID is not found or if the tutorial has no students.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get tutorial based on tutorialId given
        Tutorial tutorial = model.getTAbs().getTutorialList().stream()
                .filter(tut -> tut.getTutorialId().equals(tutorialId)).findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format("A tutorial with the TUTORIAL_ID %s does not exist", tutorialId.id)
                ));

        // Convert the set of students to a list to preserve order
        List<Student> studentList = new ArrayList<>(tutorial.getStudents());

        // Throw an exception if the student list is empty
        if (studentList.size() == 0) {
            throw new CommandException(
                    String.format("The tutorial %s has no students enrolled.", tutorialId.id)
            );
        }

        // Build the string representation of the list of students
        for (int i = 0; i < studentList.size(); i++) {
            listOfStudents = listOfStudents + (i + 1) + ". " + studentList.get(i).studentId + "\n";
        }

        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_TEMPLATE, tutorialId) + listOfStudents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListStudentsCommand)) {
            return false;
        }

        if (other instanceof ListStudentsCommand otherListStudentsCommand) {
            return tutorialId.equals(otherListStudentsCommand.tutorialId);
        }

        return false;
    }

    /**
     * Returns a string representation of the ListStudentsCommand.
     * This method is required to pass the ListStudentsCommandTest.toStringMethod() test.
     */
    @Override
    public String toString() {
        return ListStudentsCommand.class.getCanonicalName() + "{tutorialId=" + tutorialId + "}";
    }
}
