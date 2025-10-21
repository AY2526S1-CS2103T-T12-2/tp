package seedu.tabs.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * A utility class to help with building EditTutorialDescriptor objects.
 */
public class EditTutorialDescriptorBuilder {

    private EditTutorialDescriptor descriptor;

    public EditTutorialDescriptorBuilder() {
        descriptor = new EditTutorialDescriptor();
    }

    public EditTutorialDescriptorBuilder(EditTutorialDescriptor descriptor) {
        this.descriptor = new EditTutorialDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTutorialDescriptor} with fields containing {@code tutorial}'s details
     */
    public EditTutorialDescriptorBuilder(Tutorial aTutorial) {
        descriptor = new EditTutorialDescriptor();
        descriptor.setId(aTutorial.getTutorialId());
        descriptor.setModuleCode(aTutorial.getModuleCode());
        descriptor.setDate(aTutorial.getDate());
        descriptor.setStudents(aTutorial.getStudents());
    }

    /**
     * Sets the {@code TutorialId} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withId(String id) {
        descriptor.setId(new TutorialId(id));
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withModuleCode(String moduleCode) {
        descriptor.setModuleCode(new ModuleCode(moduleCode));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<Student>} and set it to the {@code EditTutorialDescriptor}
     * that we are building.
     */
    public EditTutorialDescriptorBuilder withStudents(String... students) {
        Set<Student> studentSet = Stream.of(students).map(Student::new).collect(Collectors.toSet());
        descriptor.setStudents(studentSet);
        return this;
    }

    public EditTutorialDescriptor build() {
        return descriptor;
    }
}
