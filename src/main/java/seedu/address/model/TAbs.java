package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.UniqueTutorialList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameTutorial comparison)
 */
public class TAbs implements ReadOnlyTAbs {

    private final UniqueTutorialList tutorials;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tutorials = new UniqueTutorialList();
    }

    public TAbs() {}

    /**
     * Creates an TAbs using the Tutorials in the {@code toBeCopied}
     */
    public TAbs(ReadOnlyTAbs toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the tutorial list with {@code tutorials}.
     * {@code tutorials} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> aTutorials) {
        this.tutorials.setTutorials(aTutorials);
    }

    /**
     * Resets the existing data of this {@code TAbs} with {@code newData}.
     */
    public void resetData(ReadOnlyTAbs newData) {
        requireNonNull(newData);

        setTutorials(newData.getTutorialList());
    }

    //// tutorial-level operations

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the address book.
     */
    public boolean hasTutorial(Tutorial aTutorial) {
        requireNonNull(aTutorial);
        return tutorials.contains(aTutorial);
    }

    /**
     * Adds a tutorial to the address book.
     * The tutorial must not already exist in the address book.
     */
    public void addTutorial(Tutorial p) {
        tutorials.add(p);
    }

    /**
     * Replaces the given tutorial {@code target} in the list with {@code editedTutorial}.
     * {@code target} must exist in the address book.
     * The tutorial identity of {@code editedTutorial} must not be the same as another existing tutorial in the address book.
     */
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireNonNull(editedTutorial);

        tutorials.setTutorial(target, editedTutorial);
    }

    /**
     * Removes {@code key} from this {@code TAbs}.
     * {@code key} must exist in the address book.
     */
    public void removeTutorial(Tutorial key) {
        tutorials.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorials", tutorials)
                .toString();
    }

    @Override
    public ObservableList<Tutorial> getTutorialList() {
        return tutorials.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TAbs)) {
            return false;
        }

        TAbs otherTAbs = (TAbs) other;
        return tutorials.equals(otherTAbs.tutorials);
    }

    @Override
    public int hashCode() {
        return tutorials.hashCode();
    }
}
