package seedu.address.model.tutorial;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Tutorial in TAbs.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {

    // Identity fields
    private final TutorialId tutorialId;
    private final Phone phone;
    private final Date date;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Tutorial(TutorialId tutorialId, Phone phone, Date date, Address address, Set<Tag> tags) {
        requireAllNonNull(tutorialId, phone, date, address, tags);
        this.tutorialId = tutorialId;
        this.phone = phone;
        this.date = date;
        this.address = address;
        this.tags.addAll(tags);
    }

    public TutorialId getName() {
        return tutorialId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Date getEmail() {
        return date;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both classes have the same name.
     * This defines a weaker notion of equality between two tutorials.
     *
     * TO BE CHANGED.
     */
    public boolean isSameTutorial(Tutorial otherTutorial) {
        if (otherTutorial == this) {
            return true;
        }

        return otherTutorial != null
                && otherTutorial.getName().equals(getName());
    }

    /**
     * Returns true if both classes have the same identity and data fields.
     * This defines a stronger notion of equality between two classes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return tutorialId.equals(otherTutorial.tutorialId)
                && phone.equals(otherTutorial.phone)
                && date.equals(otherTutorial.date)
                && address.equals(otherTutorial.address)
                && tags.equals(otherTutorial.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tutorialId, phone, date, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", tutorialId)
                .add("phone", phone)
                .add("date", date)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
