package seedu.tabs.model;

import javafx.collections.ObservableList;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * Unmodifiable view of an TAbs
 */
public interface ReadOnlyTAbs {

    /**
     * Returns an unmodifiable view of the tutorials list.
     * This list will not contain any duplicate tutorials.
     */
    ObservableList<Tutorial> getTutorialList();

}
