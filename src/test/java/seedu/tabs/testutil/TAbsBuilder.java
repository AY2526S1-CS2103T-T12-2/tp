package seedu.tabs.testutil;

import seedu.tabs.model.TAbs;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * A utility class to help with building TAbs objects.
 * Example usage: <br>
 * {@code TAbs ab = new TAbsBuilder().withTutorial("John", "Doe").build();}
 */
public class TAbsBuilder {

    private TAbs tabs;

    public TAbsBuilder() {
        tabs = new TAbs();
    }

    public TAbsBuilder(TAbs tabs) {
        this.tabs = tabs;
    }

    /**
     * Adds a new {@code Tutorial} to the {@code TAbs} that we are building.
     */
    public TAbsBuilder withTutorial(Tutorial aTutorial) {
        tabs.addTutorial(aTutorial);
        return this;
    }

    public TAbs build() {
        return tabs;
    }
}
