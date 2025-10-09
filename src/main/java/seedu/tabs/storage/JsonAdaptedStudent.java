package seedu.tabs.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.model.student.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    private final String studentId;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code studentId}.
     */
    @JsonCreator
    public JsonAdaptedStudent(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        studentId = source.studentId;
    }

    @JsonValue
    public String getstudentId() {
        return studentId;
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        if (!Student.isValidStudentId(studentId)) {
            throw new IllegalValueException(Student.MESSAGE_CONSTRAINTS);
        }
        return new Student(studentId);
    }

}
