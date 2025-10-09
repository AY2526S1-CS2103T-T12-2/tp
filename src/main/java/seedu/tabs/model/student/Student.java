package seedu.tabs.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student in the TAbs.
 * Guarantees: immutable; name is valid as declared in {@link #isValidStudentName(String)}
 */
public class Student {

    public static final String MESSAGE_CONSTRAINTS =
                    "Invalid format: Student ids should follow the format, AXXXXXXX$,\n" +
                    "where the X's represent any 7 single digit numbers\n" +
                    "and the $ represents any capital letter." ;
    public static final String VALIDATION_REGEX = "A\\d{7}[A-Z]";

    public final String studentName;

    /**
     * Constructs a {@code Student}.
     *
     * @param studentName A valid student name.
     */
    public Student(String studentName) {
        requireNonNull(studentName);
        checkArgument(isValidStudentName(studentName), MESSAGE_CONSTRAINTS);
        this.studentName = studentName;
    }

    /**
     * Returns true if a given string is a valid student name.
     */
    public static boolean isValidStudentName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return studentName.equals(otherStudent.studentName);
    }

    @Override
    public int hashCode() {
        return studentName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + studentName + ']';
    }

}
