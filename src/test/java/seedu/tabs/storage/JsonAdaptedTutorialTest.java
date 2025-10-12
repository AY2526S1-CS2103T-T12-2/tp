package seedu.tabs.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tabs.storage.JsonAdaptedTutorial.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;

public class JsonAdaptedTutorialTest {
    private static final String INVALID_TUTORIAL_ID = "R@chel";
    private static final String INVALID_MODULE_CODE = "+651234";
    private static final String INVALID_DATE = "example.com";
    private static final String INVALID_STUDENT = "#friend";

    private static final String VALID_TUTORIAL_ID = BENSON.getTutorialId().toString();
    private static final String VALID_MODULE_CODE = BENSON.getModuleCode().toString();
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final List<JsonAdaptedStudent> VALID_STUDENTS = BENSON.getStudents().stream()
            .map(JsonAdaptedStudent::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTutorialDetails_returnsTutorial() throws Exception {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(BENSON);
        assertEquals(BENSON, tutorial.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial =
                new JsonAdaptedTutorial(INVALID_TUTORIAL_ID, VALID_MODULE_CODE, VALID_DATE,
                        VALID_STUDENTS);
        String expectedMessage = TutorialId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(null, VALID_MODULE_CODE, VALID_DATE,
                VALID_STUDENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TutorialId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial =
                new JsonAdaptedTutorial(VALID_TUTORIAL_ID, INVALID_MODULE_CODE, VALID_DATE,
                        VALID_STUDENTS);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_ID, null, VALID_DATE,
                VALID_STUDENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial =
                new JsonAdaptedTutorial(VALID_TUTORIAL_ID, VALID_MODULE_CODE, INVALID_DATE,
                        VALID_STUDENTS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedTutorial tutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_ID, VALID_MODULE_CODE, null,
                VALID_STUDENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, tutorial::toModelType);
    }

    @Test
    public void toModelType_invalidStudents_throwsIllegalValueException() {
        List<JsonAdaptedStudent> invalidStudents = new ArrayList<>(VALID_STUDENTS);
        invalidStudents.add(new JsonAdaptedStudent(INVALID_STUDENT));
        JsonAdaptedTutorial tutorial =
                new JsonAdaptedTutorial(VALID_TUTORIAL_ID, VALID_MODULE_CODE, VALID_DATE,
                        invalidStudents);
        assertThrows(IllegalValueException.class, tutorial::toModelType);
    }

}
