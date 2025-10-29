
package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_A101;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_TEST_T456;

import org.junit.jupiter.api.Test;

import seedu.tabs.testutil.TutorialBuilder;

public class TutorialTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Tutorial aTutorial = new TutorialBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> aTutorial.getStudents().remove(0));
    }

    @Test
    public void isSameTutorial() {
        // same object -> returns true
        assertTrue(TUTORIAL_CS2103T_A101.isSameTutorial(TUTORIAL_CS2103T_A101));

        // null -> returns false
        assertFalse(TUTORIAL_CS2103T_A101.isSameTutorial(null));

        // same id, all other attributes different -> returns true
        Tutorial editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101)
                .withModuleCode(VALID_MODULE_CODE_MA1521).withDate(VALID_DATE_T456)
                .withStudents(VALID_STUDENT_A).build();
        assertTrue(TUTORIAL_CS2103T_A101.isSameTutorial(editedAlice));

        // id differs in case, all other attributes same -> returns true (case insensitive)
        Tutorial editedBob = new TutorialBuilder(TUTORIAL_TEST_T456)
                .withId(VALID_TUTORIAL_T456.toLowerCase()).build();
        assertTrue(TUTORIAL_TEST_T456.isSameTutorial(editedBob));

        // different id, all other attributes same -> returns false
        editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withId(VALID_TUTORIAL_T456).build();
        assertFalse(TUTORIAL_CS2103T_A101.isSameTutorial(editedAlice));

        // completely different id -> returns false
        editedBob = new TutorialBuilder(TUTORIAL_TEST_T456).withId("C999").build();
        assertFalse(TUTORIAL_TEST_T456.isSameTutorial(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Tutorial aliceCopy = new TutorialBuilder(TUTORIAL_CS2103T_A101).build();
        assertTrue(TUTORIAL_CS2103T_A101.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TUTORIAL_CS2103T_A101.equals(TUTORIAL_CS2103T_A101));

        // null -> returns false
        assertFalse(TUTORIAL_CS2103T_A101.equals(null));

        // different type -> returns false
        assertFalse(TUTORIAL_CS2103T_A101.equals(5));

        // different tutorial -> returns false
        assertFalse(TUTORIAL_CS2103T_A101.equals(TUTORIAL_TEST_T456));

        // different id -> returns false
        Tutorial editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withId(VALID_TUTORIAL_T456).build();
        assertFalse(TUTORIAL_CS2103T_A101.equals(editedAlice));

        // different moduleCode -> returns false
        editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withModuleCode(VALID_MODULE_CODE_MA1521).build();
        assertFalse(TUTORIAL_CS2103T_A101.equals(editedAlice));

        // different date -> returns false
        editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withDate(VALID_DATE_T456).build();
        assertFalse(TUTORIAL_CS2103T_A101.equals(editedAlice));

        // different students -> returns false
        editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withStudents(VALID_STUDENT_B).build();
        assertFalse(TUTORIAL_CS2103T_A101.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Tutorial.class.getCanonicalName() + "{tutorialId=" + TUTORIAL_CS2103T_A101.getTutorialId()
                + ", moduleCode=" + TUTORIAL_CS2103T_A101.getModuleCode()
                + ", date=" + TUTORIAL_CS2103T_A101.getDate()
                + ", students=" + TUTORIAL_CS2103T_A101.getStudents() + "}";
        assertEquals(expected, TUTORIAL_CS2103T_A101.toString());
    }
}
