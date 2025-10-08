package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.address.model.tutorial.Address;
import seedu.address.model.tutorial.Date;
import seedu.address.model.tutorial.TutorialId;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.ModuleCode;
import seedu.address.model.tag.Tag;

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
        descriptor.setName(aTutorial.getName());
        descriptor.setModuleCode(aTutorial.getModuleCode());
        descriptor.setEmail(aTutorial.getEmail());
        descriptor.setAddress(aTutorial.getAddress());
        descriptor.setTags(aTutorial.getTags());
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
    public EditTutorialDescriptorBuilder withEmail(String date) {
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTutorialDescriptor}
     * that we are building.
     */
    public EditTutorialDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTutorialDescriptor build() {
        return descriptor;
    }
}
