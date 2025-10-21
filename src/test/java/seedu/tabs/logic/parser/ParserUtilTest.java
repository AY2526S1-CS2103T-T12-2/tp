package seedu.tabs.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;

public class ParserUtilTest {
    private static final String INVALID_TUTORIAL = "R@chel";
    private static final String INVALID_MODULE_CODE = "651234";
    private static final String INVALID_DATE = "example.com";
    private static final String INVALID_STUDENT = "A123";

    private static final String VALID_NAME = "C123";
    private static final String VALID_MODULE_CODE = "CS2103T";
    private static final String VALID_DATE = "2025-01-15";
    private static final String VALID_STUDENT_1 = "A1231231Y";
    private static final String VALID_STUDENT_2 = "A3213213Y";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTutorialId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTutorialId((String) null));
    }

    @Test
    public void parseTutorialId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialId(INVALID_TUTORIAL));
    }

    @Test
    public void parseTutorialId_validValueWithoutWhitespace_returnsTutorialId() throws Exception {
        TutorialId expectedTutorialId = new TutorialId(VALID_NAME);
        assertEquals(expectedTutorialId, ParserUtil.parseTutorialId(VALID_NAME));
    }

    @Test
    public void parseTutorialId_validValueWithWhitespace_returnsTrimmedTutorialId() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        TutorialId expectedTutorialId = new TutorialId(VALID_NAME);
        assertEquals(expectedTutorialId, ParserUtil.parseTutorialId(nameWithWhitespace));
    }

    @Test
    public void parseModuleCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode((String) null));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithoutWhitespace_returnsModuleCode() throws Exception {
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(VALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedModuleCode() throws Exception {
        String moduleCodeWithWhitespace = WHITESPACE + VALID_MODULE_CODE + WHITESPACE;
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(moduleCodeWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseStudent_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudent(null));
    }

    @Test
    public void parseStudent_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudent(INVALID_STUDENT));
    }

    @Test
    public void parseStudent_validValueWithoutWhitespace_returnsStudent() throws Exception {
        Student expectedStudent = new Student(VALID_STUDENT_1);
        assertEquals(expectedStudent, ParserUtil.parseStudent(VALID_STUDENT_1));
    }

    @Test
    public void parseStudent_validValueWithWhitespace_returnsTrimmedStudent() throws Exception {
        String studentWithWhitespace = WHITESPACE + VALID_STUDENT_1 + WHITESPACE;
        Student expectedStudent = new Student(VALID_STUDENT_1);
        assertEquals(expectedStudent, ParserUtil.parseStudent(studentWithWhitespace));
    }

    @Test
    public void parseStudents_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudents(null));
    }

    @Test
    public void parseStudents_collectionWithInvalidStudents_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseStudents(Arrays.asList(VALID_STUDENT_1, INVALID_STUDENT)));
    }

    @Test
    public void parseStudents_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseStudents(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseStudents_collectionWithValidStudents_returnsStudentSet() throws Exception {
        Set<Student> actualStudentSet = ParserUtil.parseStudents(Arrays.asList(VALID_STUDENT_1, VALID_STUDENT_2));
        Set<Student> expectedStudentSet = new HashSet<Student>(Arrays.asList(new Student(VALID_STUDENT_1),
                new Student(VALID_STUDENT_2)));

        assertEquals(expectedStudentSet, actualStudentSet);
    }
}
