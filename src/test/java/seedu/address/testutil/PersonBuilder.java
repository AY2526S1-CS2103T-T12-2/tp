package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tutorial.Address;
import seedu.address.model.tutorial.Date;
import seedu.address.model.tutorial.TutorialId;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.ModuleCode;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Tutorial objects.
 */
public class TutorialBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private TutorialId tutorialId;
    private ModuleCode moduleCode;
    private Date date;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code TutorialBuilder} with the default details.
     */
    public TutorialBuilder() {
        tutorialId = new TutorialId(DEFAULT_NAME);
        moduleCode = new ModuleCode(DEFAULT_PHONE);
        date = new Date(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TutorialBuilder with the data of {@code tutorialToCopy}.
     */
    public TutorialBuilder(Tutorial tutorialToCopy) {
        tutorialId = tutorialToCopy.getName();
        moduleCode = tutorialToCopy.getModuleCode();
        date = tutorialToCopy.getEmail();
        address = tutorialToCopy.getAddress();
        tags = new HashSet<>(tutorialToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withName(String name) {
        this.tutorialId = new TutorialId(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withAddress(String address) {
        this.address = new Address(address);
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
     * Sets the {@code Email} of the {@code Tutorial} that we are building.
     */
    public TutorialBuilder withEmail(String date) {
        this.date = new Date(date);
        return this;
    }

    public Tutorial build() {
        return new Tutorial(tutorialId, moduleCode, date, address, tags);
    }

}
