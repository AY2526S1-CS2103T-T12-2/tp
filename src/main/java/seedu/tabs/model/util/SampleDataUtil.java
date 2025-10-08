package seedu.tabs.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Address;
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
            new Tutorial(new TutorialId("Alex Yeoh"), new ModuleCode("87438807"),
                    new Date("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getStudentSet("friends")),
            new Tutorial(new TutorialId("Bernice Yu"), new ModuleCode("99272758"),
                    new Date("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getStudentSet("colleagues", "friends")),
            new Tutorial(new TutorialId("Charlotte Oliveiro"), new ModuleCode("93210283"),
                    new Date("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getStudentSet("neighbours")),
            new Tutorial(new TutorialId("David Li"), new ModuleCode("91031282"),
                    new Date("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getStudentSet("family")),
            new Tutorial(new TutorialId("Irfan Ibrahim"), new ModuleCode("92492021"),
                    new Date("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getStudentSet("classmates")),
            new Tutorial(new TutorialId("Roy Balakrishnan"), new ModuleCode("92624417"),
                    new Date("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getStudentSet("colleagues"))
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Student> getStudentSet(String... strings) {
        return Arrays.stream(strings)
                .map(Student::new)
                .collect(Collectors.toSet());
    }

}
