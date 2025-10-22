package seedu.tabs.testutil;

import seedu.tabs.logic.commands.EditTutorialCommand.EditTutorialDescriptor;
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
        descriptor.setDate(aTutorial.getDate());
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
     * Sets the {@code Date} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    public EditTutorialDescriptor build() {
        return descriptor;
    }
}
