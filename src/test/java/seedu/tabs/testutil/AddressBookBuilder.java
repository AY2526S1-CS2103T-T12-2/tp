package seedu.tabs.testutil;

import seedu.tabs.model.TAbs;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TAbs ab = new TAbsBuilder().withTutorial("John", "Doe").build();}
 */
public class TAbsBuilder {

    private TAbs TAbs;

    public TAbsBuilder() {
        TAbs = new TAbs();
    }

    public TAbsBuilder(TAbs TAbs) {
        this.TAbs = TAbs;
    }

    /**
     * Adds a new {@code Tutorial} to the {@code TAbs} that we are building.
     */
    public TAbsBuilder withTutorial(Tutorial aTutorial) {
        TAbs.addTutorial(aTutorial);
        return this;
    }

    public TAbs build() {
        return TAbs;
    }
}
