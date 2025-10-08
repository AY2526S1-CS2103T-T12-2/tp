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
import seedu.tabs.model.tutorial.Address;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_DATE = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        TutorialId expectedTutorialId = new TutorialId(VALID_NAME);
        assertEquals(expectedTutorialId, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        TutorialId expectedTutorialId = new TutorialId(VALID_NAME);
        assertEquals(expectedTutorialId, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseModuleCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode((String) null));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_PHONE));
    }

    @Test
    public void parseModuleCode_validValueWithoutWhitespace_returnsModuleCode() throws Exception {
        ModuleCode expectedModuleCode = new ModuleCode(VALID_PHONE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(VALID_PHONE));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedModuleCode() throws Exception {
        String moduleCodeWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        ModuleCode expectedModuleCode = new ModuleCode(VALID_PHONE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(moduleCodeWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_DATE));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseEmail(VALID_DATE));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseEmail(dateWithWhitespace));
    }

    @Test
    public void parseStudent_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudent(null));
    }

    @Test
    public void parseStudent_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudent(INVALID_TAG));
    }

    @Test
    public void parseStudent_validValueWithoutWhitespace_returnsStudent() throws Exception {
        Student expectedStudent = new Student(VALID_TAG_1);
        assertEquals(expectedStudent, ParserUtil.parseStudent(VALID_TAG_1));
    }

    @Test
    public void parseStudent_validValueWithWhitespace_returnsTrimmedStudent() throws Exception {
        String studentWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Student expectedStudent = new Student(VALID_TAG_1);
        assertEquals(expectedStudent, ParserUtil.parseStudent(studentWithWhitespace));
    }

    @Test
    public void parseStudents_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudents(null));
    }

    @Test
    public void parseStudents_collectionWithInvalidStudents_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudents(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseStudents_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseStudents(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseStudents_collectionWithValidStudents_returnsStudentSet() throws Exception {
        Set<Student> actualStudentSet = ParserUtil.parseStudents(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Student> expectedStudentSet = new HashSet<Student>(Arrays.asList(new Student(VALID_TAG_1),
                new Student(VALID_TAG_2)));

        assertEquals(expectedStudentSet, actualStudentSet);
    }
}
