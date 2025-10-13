package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists all the students in a tutorial"
            + "Parameters: "
            + PREFIX_TUTORIAL_ID + "TUTORIAL ID";

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
                        String.format("A tutorial with the TUTORIAL_ID %s does not exist", tutorialId.fullName)
                ));

        // Convert the set of students to a list to preserve order
        List<Student> studentList = new ArrayList<>(tutorial.getStudents());

        // Throw an exception if the student list is empty
        if (studentList.size() == 0) {
            throw new CommandException(
                    String.format("Displaying all students enrolled in tutorial %s", tutorialId.fullName)
            );
        }

        // Build the string representation of the list of students
        for (int i = 0; i < studentList.size(); i++) {
            listOfStudents = listOfStudents + (i + 1) + ". " + studentList.get(i).studentId + "\n";
        }

        return new CommandResult(listOfStudents);
    }
}
