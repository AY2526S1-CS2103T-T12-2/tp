package seedu.tabs.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.model.student.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    private final String studentName;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code studentName}.
     */
    @JsonCreator
    public JsonAdaptedStudent(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        studentName = source.studentName;
    }

    @JsonValue
    public String getStudentName() {
        return studentName;
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        if (!Student.isValidStudentName(studentName)) {
            throw new IllegalValueException(Student.MESSAGE_CONSTRAINTS);
        }
        return new Student(studentName);
    }

}
