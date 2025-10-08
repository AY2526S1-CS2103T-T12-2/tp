package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.address.model.tutorial.Address;
import seedu.address.model.tutorial.Email;
import seedu.address.model.tutorial.Name;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.Phone;
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
        descriptor.setPhone(aTutorial.getPhone());
        descriptor.setEmail(aTutorial.getEmail());
        descriptor.setAddress(aTutorial.getAddress());
        descriptor.setTags(aTutorial.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditTutorialDescriptor} that we are building.
     */
    public EditTutorialDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
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
