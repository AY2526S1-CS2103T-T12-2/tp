package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.ALICE;
import static seedu.tabs.testutil.TypicalTutorials.BOB;

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
        assertTrue(ALICE.isSameTutorial(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTutorial(null));

        // same name, all other attributes different -> returns true
        Tutorial editedAlice = new TutorialBuilder(ALICE).withModuleCode(VALID_PHONE_BOB).withDate(VALID_DATE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withStudents(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTutorial(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new TutorialBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTutorial(editedAlice));

        // name differs in case, all other attributes same -> returns true (case insensitive)
        Tutorial editedBob = new TutorialBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameTutorial(editedBob));

        // completely different name -> returns false
        editedBob = new TutorialBuilder(BOB).withName("C999").build();
        assertFalse(BOB.isSameTutorial(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Tutorial aliceCopy = new TutorialBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different tutorial -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Tutorial editedAlice = new TutorialBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different moduleCode -> returns false
        editedAlice = new TutorialBuilder(ALICE).withModuleCode(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new TutorialBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tabs -> returns false
        editedAlice = new TutorialBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different students -> returns false
        editedAlice = new TutorialBuilder(ALICE).withStudents(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Tutorial.class.getCanonicalName() + "{tutorialId=" + ALICE.getTutorialId()
                + ", moduleCode=" + ALICE.getModuleCode()
                + ", date=" + ALICE.getDate() + ", tabs=" + ALICE.getAddress()
                + ", students=" + ALICE.getStudents() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
