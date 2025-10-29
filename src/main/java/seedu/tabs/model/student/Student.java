package seedu.tabs.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student in the TAbs.
 * Guarantees: immutable; student id is valid as declared in {@link #isValidStudentId(String)}
 */
public class Student {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid format: Student IDs should follow the format, 'AXXXXXXX&',\n"
                    + "where the 'X's represent any 7 single-digit numbers\n"
                    + "and the '&' represents any capital letter.";
    public static final String VALIDATION_REGEX = "A\\d{7}[A-Z]";

    public final String studentId;
    private boolean isPresent;

    /**
     * Constructs a {@code Student}.
     *
     * @param studentId A valid student ID.
     */
    public Student(String studentId) {
        requireNonNull(studentId);
        checkArgument(isValidStudentId(studentId), MESSAGE_CONSTRAINTS);
        this.studentId = studentId;
        this.isPresent = false;
    }

    /**
     * Constructs a {@code Student}.
     *
     * @param studentId A valid student ID
     * @param isPresent indicates if the student is present
     */
    public Student(String studentId, boolean isPresent) {
        this(studentId);
        this.isPresent = isPresent;
    }

    /**
     * Returns true if a given string is a valid student ID.
     */
    public static boolean isValidStudentId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public void mark() {
        this.isPresent = true;
    }

    public void unmark() {
        this.isPresent = false;
    }

    public boolean getAttendance() {
        return this.isPresent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return studentId.equals(otherStudent.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + studentId + "]";
    }

}
