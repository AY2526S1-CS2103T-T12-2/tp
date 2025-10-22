package seedu.tabs.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.util.SampleDataUtil;

/**
 * A utility class to help with building Tutorial objects.
 */
public class TutorialBuilder {

    public static final String DEFAULT_TUTORIAL_ID = "C999";
    public static final String DEFAULT_MODULE_CODE = "CS9999";
    public static final String DEFAULT_DATE = "2025-12-31";

    private TutorialId tutorialId;
    private ModuleCode moduleCode;
    private Date date;
    private Set<Student> students;

    /**
     * Creates a {@code TutorialBuilder} with the default details.
     */
    public TutorialBuilder() {
        tutorialId = new TutorialId(DEFAULT_TUTORIAL_ID);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        date = new Date(DEFAULT_DATE);
        students = new HashSet<>();
    }

    /**
     * Initializes the TutorialBuilder with the data of {@code tutorialToCopy}.
     */
    public TutorialBuilder(Tutorial tutorialToCopy) {
        tutorialId = tutorialToCopy.getTutorialId();
        moduleCode = tutorialToCopy.getModuleCode();
        date = tutorialToCopy.getDate();
        students = new HashSet<>(tutorialToCopy.getStudents());
    }

    /**
     * Sets the {@code Name} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withId(String id) {
        this.tutorialId = new TutorialId(id);
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<Student>} and set it to the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withStudents(String... students) {
        this.students = SampleDataUtil.getStudentSet(students);
        return this;
    }

    /**
     * Takes in a set of {@code students} to and set it to the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withStudents(Set<Student> students) {
        this.students = students;
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Tutorial build() {
        return new Tutorial(tutorialId, moduleCode, date, students);
    }

}
