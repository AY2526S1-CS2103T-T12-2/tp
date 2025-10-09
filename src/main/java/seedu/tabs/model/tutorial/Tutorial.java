package seedu.tabs.model.tutorial;

import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.model.student.Student;

/**
 * Represents a Tutorial in TAbs.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {

    // Identity fields
    private final TutorialId tutorialId;
    private final ModuleCode moduleCode;
    private final Date date;

    // Data fields
    private final Address address;
    private final Set<Student> students = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Tutorial(TutorialId tutorialId, ModuleCode moduleCode, Date date, Address address, Set<Student> students) {
        requireAllNonNull(tutorialId, moduleCode, date);
        this.tutorialId = tutorialId;
        this.moduleCode = moduleCode;
        this.date = date;
        this.address = address != null ? address : new Address("No address specified");
        this.students.addAll(students != null ? students : new HashSet<>());
    }

    public TutorialId getTutorialId() {
        return tutorialId;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Date getDate() {
        return date;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
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
                && otherTutorial.getTutorialId().equals(getTutorialId());
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
                && moduleCode.equals(otherTutorial.moduleCode)
                && date.equals(otherTutorial.date)
                && address.equals(otherTutorial.address)
                && students.equals(otherTutorial.students);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tutorialId, moduleCode, date, address, students);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialId", tutorialId)
                .add("moduleCode", moduleCode)
                .add("date", date)
                .add("tabs", address)
                .add("students", students)
                .toString();
    }

}
