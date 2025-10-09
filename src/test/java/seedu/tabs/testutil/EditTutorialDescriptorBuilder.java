package seedu.tabs.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Address;
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
        descriptor.setName(aTutorial.getTutorialId());
        descriptor.setModuleCode(aTutorial.getModuleCode());
        descriptor.setEmail(aTutorial.getDate());
        descriptor.setAddress(aTutorial.getAddress());
        descriptor.setStudents(aTutorial.getStudents());
    }

    /**
     * Sets the {@code Name} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withName(String name) {
        descriptor.setName(new TutorialId(name));
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
     * Sets the {@code Email} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withDate(String date) {
        descriptor.setEmail(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
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
