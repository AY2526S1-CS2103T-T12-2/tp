package seedu.tabs.model.student;

import static seedu.tabs.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Student(null));
    }

    @Test
    public void constructor_invalidStudentId_throwsIllegalArgumentException() {
        String invalidStudentId = "";
        assertThrows(IllegalArgumentException.class, () -> new Student(invalidStudentId));
    }

    @Test
    public void isValidStudentId() {
        // null student id
        assertThrows(NullPointerException.class, () -> Student.isValidStudentId(null));
    }

}
