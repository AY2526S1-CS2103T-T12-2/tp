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

        if (studentList.size() == 0) {
            throw new CommandException(
                    String.format("A tutorial with the TUTORIAL_ID %s does not exist", tutorialId.fullName)
            );
        }

        for (int i = 0; i < studentList.size(); i++) {
            listOfStudents = listOfStudents + (i + 1) + ". " + studentList.get(i).studentId + "\n";
        }

        return new CommandResult(listOfStudents);
    }
}
