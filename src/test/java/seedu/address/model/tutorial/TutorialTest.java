package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutorials.ALICE;
import static seedu.address.testutil.TypicalTutorials.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TutorialBuilder;

public class TutorialTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Tutorial aTutorial = new TutorialBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> aTutorial.getTags().remove(0));
    }

    @Test
    public void isSameTutorial() {
        // same object -> returns true
        assertTrue(ALICE.isSameTutorial(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTutorial(null));

        // same name, all other attributes different -> returns true
        Tutorial editedAlice = new TutorialBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTutorial(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new TutorialBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTutorial(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Tutorial editedBob = new TutorialBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameTutorial(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new TutorialBuilder(BOB).withName(nameWithTrailingSpaces).build();
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

        // different phone -> returns false
        editedAlice = new TutorialBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TutorialBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TutorialBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TutorialBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Tutorial.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
