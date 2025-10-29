package seedu.tabs.testutil;

import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import seedu.tabs.logic.commands.AddTutorialCommand;
import seedu.tabs.logic.commands.EditTutorialCommand.EditTutorialDescriptor;
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
        sb.append(TUTORIAL_ID.prefix + aTutorial.getTutorialId().id + " ");
        sb.append(MODULE_CODE.prefix + aTutorial.getModuleCode().value + " ");
        sb.append(DATE.prefix + aTutorial.getDate().value + " ");
        aTutorial.getStudents().stream().forEach(
                s -> sb.append(STUDENT.prefix + s.studentId + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTutorialDescriptor}'s details.
     */
    public static String getEditTutorialDescriptorDetails(EditTutorialDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getId().ifPresent(tutorialId -> sb.append(TUTORIAL_ID.prefix).append(tutorialId.id).append(" "));
        descriptor.getModuleCode().ifPresent(moduleCode -> sb.append(MODULE_CODE.prefix)
                .append(moduleCode.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(DATE.prefix).append(date.value).append(" "));
        return sb.toString();
    }
}
