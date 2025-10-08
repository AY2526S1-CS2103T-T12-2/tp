package seedu.address.testutil;

import seedu.address.model.TAbs;
import seedu.address.model.tutorial.Tutorial;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withTutorial("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TAbs TAbs;

    public AddressBookBuilder() {
        TAbs = new TAbs();
    }

    public AddressBookBuilder(TAbs TAbs) {
        this.TAbs = TAbs;
    }

    /**
     * Adds a new {@code Tutorial} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTutorial(Tutorial aTutorial) {
        TAbs.addTutorial(aTutorial);
        return this;
    }

    public TAbs build() {
        return TAbs;
    }
}
