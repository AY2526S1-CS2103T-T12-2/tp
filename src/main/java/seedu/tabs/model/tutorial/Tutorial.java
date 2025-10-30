package seedu.tabs.model.tutorial;

import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.model.student.Student;

/**
 * Represents a tutorial in TAbs.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutorial {

    // Identity fields
    private final TutorialId tutorialId;
    private final ModuleCode moduleCode;
    private final Date date;

    // Data fields
    private final Set<Student> students = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Tutorial(TutorialId tutorialId, ModuleCode moduleCode, Date date, Set<Student> students) {
        requireAllNonNull(tutorialId, moduleCode, date);
        this.tutorialId = tutorialId;
        this.moduleCode = moduleCode;
        this.date = date;
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

    /**
     * Returns an immutable student set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    /**
     * Returns a string representation of the set of students within the {@code Tutorial}.
     * If there are no students, display "None".
     */
    public String getStudentsAsString() {
        final StringJoiner joiner = new StringJoiner(", ");
        if (students.isEmpty()) {
            return "None";
        } else {
            students.forEach(student -> joiner.add(student.toString()));
            return joiner.toString();
        }
    }

    /**
     * Returns the number of students in the tutorial.
     */
    public int getNumberOfStudents() {
        return students.size();
    }

    /**
     * Marks all the students in the {@code Tutorial} as present.
     */
    public void markAllStudents() {
        students.forEach(Student::mark);
    }

    /**
     * Unmarks all the students in the {@code Tutorial}.
     */
    public void unmarkAllStudents() {
        students.forEach(Student::unmark);
    }

    /**
     * Returns true if both tutorials have the same id.
     * This defines a weaker notion of equality between two tutorials.
     */
    public boolean isSameTutorial(Tutorial otherTutorial) {
        if (otherTutorial == this) {
            return true;
        }

        return otherTutorial != null
                && otherTutorial.getTutorialId().equals(getTutorialId());
    }

    /**
     * Returns true if both tutorials have the same class details and list of students.
     * This defines a stronger notion of equality between two tutorials.
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
                && students.equals(otherTutorial.students);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tutorialId, moduleCode, date, students);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialId", tutorialId)
                .add("moduleCode", moduleCode)
                .add("date", date)
                .add("students", students)
                .toString();
    }

}
