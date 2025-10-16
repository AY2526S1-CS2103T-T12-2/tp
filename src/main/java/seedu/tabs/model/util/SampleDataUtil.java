package seedu.tabs.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Contains utility methods for populating {@code TAbs} with sample data.
 */
public class SampleDataUtil {
    public static Tutorial[] getSampleTutorials() {
        return new Tutorial[] {
            new Tutorial(new TutorialId("C101"), new ModuleCode("CS2103T"),
                    new Date("2025-01-15"),
                getStudentSet("A1234567A", "A2345678B")),
            new Tutorial(new TutorialId("T102"), new ModuleCode("CS2101"),
                    new Date("2025-01-16"),
                getStudentSet("A3456789C", "A4567890D")),
            new Tutorial(new TutorialId("C103"), new ModuleCode("MA1521"),
                    new Date("2025-01-17"),
                getStudentSet("A5678901E", "A6789012F")),
            new Tutorial(new TutorialId("T104"), new ModuleCode("ST2334"),
                    new Date("2025-01-18"),
                getStudentSet("A7890123G", "A8901234H")),
            new Tutorial(new TutorialId("T105"), new ModuleCode("CS1010"),
                    new Date("2025-01-19"),
                getStudentSet("A9012345I", "A0123456J")),
            new Tutorial(new TutorialId("C106"), new ModuleCode("CS2040S"),
                    new Date("2025-01-20"),
                getStudentSet("A1357924K", "A2468013L"))
        };
    }

    public static ReadOnlyTAbs getSampleTAbs() {
        TAbs sampleAb = new TAbs();
        for (Tutorial sampleTutorial : getSampleTutorials()) {
            sampleAb.addTutorial(sampleTutorial);
        }
        return sampleAb;
    }

    /**
     * Returns a student set containing the list of strings given.
     */
    public static Set<Student> getStudentSet(String... strings) {
        return Arrays.stream(strings)
                .map(Student::new)
                .collect(Collectors.toSet());
    }

}
