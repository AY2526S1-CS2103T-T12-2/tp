package seedu.tabs.testutil;

import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.Set;

import seedu.tabs.logic.commands.AddTutorialCommand;
import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * A utility class for Tutorial.
 */
public class TutorialUtil {

    /**
     * Returns an add command string for adding the {@code tutorial}.
     */
    public static String getAddCommand(Tutorial aTutorial) {
        return AddTutorialCommand.COMMAND_WORD + " " + getTutorialDetails(aTutorial);
    }

    /**
     * Returns the part of command string for the given {@code tutorial}'s details.
     */
    public static String getTutorialDetails(Tutorial aTutorial) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TUTORIAL_ID + aTutorial.getTutorialId().id + " ");
        sb.append(PREFIX_MODULE_CODE + aTutorial.getModuleCode().value + " ");
        sb.append(PREFIX_DATE + aTutorial.getDate().value + " ");
        aTutorial.getStudents().stream().forEach(
                s -> sb.append(PREFIX_STUDENT + s.studentId + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTutorialDescriptor}'s details.
     */
    public static String getEditTutorialDescriptorDetails(EditTutorialDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(tutorialId -> sb.append(PREFIX_TUTORIAL_ID).append(tutorialId.id).append(" "));
        descriptor.getModuleCode().ifPresent(moduleCode -> sb.append(PREFIX_MODULE_CODE)
                .append(moduleCode.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        if (descriptor.getStudents().isPresent()) {
            Set<Student> students = descriptor.getStudents().get();
            if (students.isEmpty()) {
                sb.append(PREFIX_STUDENT);
            } else {
                students.forEach(s -> sb.append(PREFIX_STUDENT).append(s.studentId).append(" "));
            }
        }
        return sb.toString();
    }
}
