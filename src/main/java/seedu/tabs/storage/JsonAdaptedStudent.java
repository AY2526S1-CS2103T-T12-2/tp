package seedu.tabs.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.model.student.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    private final String studentId;
    private boolean isPresent;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code studentId}.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("studentId") String studentId,
                              @JsonProperty("isPresent") boolean isPresent) {
        this.studentId = studentId;
        this.isPresent = isPresent;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        this.studentId = source.studentId;
        this.isPresent = source.getAttendance();
    }

    public String getstudentId() {
        return studentId;
    }

    public boolean getAttendance() {
        return isPresent;
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
        return new Student(studentId, isPresent);
    }

}
