package seedu.tabs.model.student;

import static seedu.tabs.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Student(null));
    }

    @Test
    public void constructor_invalidStudentName_throwsIllegalArgumentException() {
        String invalidStudentName = "";
        assertThrows(IllegalArgumentException.class, () -> new Student(invalidStudentName));
    }

    @Test
    public void isValidStudentName() {
        // null student name
        assertThrows(NullPointerException.class, () -> Student.isValidStudentName(null));
    }

}
