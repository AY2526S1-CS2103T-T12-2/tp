package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.ALICE;
import static seedu.tabs.testutil.TypicalTutorials.BOB;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_C101;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tabs.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.tabs.model.tutorial.exceptions.TutorialNotFoundException;
import seedu.tabs.testutil.TutorialBuilder;

public class UniqueTutorialListTest {

    private final UniqueTutorialList uniqueTutorialList = new UniqueTutorialList();

    @Test
    public void contains_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.contains(null));
    }

    @Test
    public void contains_tutorialNotInList_returnsFalse() {
        assertFalse(uniqueTutorialList.contains(TUTORIAL_CS2103T_C101));
    }

    @Test
    public void contains_tutorialInList_returnsTrue() {
        uniqueTutorialList.add(TUTORIAL_CS2103T_C101);
        assertTrue(uniqueTutorialList.contains(TUTORIAL_CS2103T_C101));
    }

    @Test
    public void contains_tutorialWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTutorialList.add(TUTORIAL_CS2103T_C101);
        Tutorial editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_C101)
                .withAddress(VALID_ADDRESS_BOB).withStudents(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueTutorialList.contains(editedAlice));
    }

    @Test
    public void add_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.add(null));
    }

    @Test
    public void add_duplicateTutorial_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(ALICE);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList.add(ALICE));
    }

    @Test
    public void setTutorial_nullTargetTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorial(null, ALICE));
    }

    @Test
    public void setTutorial_nullEditedTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorial(ALICE, null));
    }

    @Test
    public void setTutorial_targetTutorialNotInList_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList.setTutorial(ALICE, ALICE));
    }

    @Test
    public void setTutorial_editedTutorialIsSameTutorial_success() {
        uniqueTutorialList.add(ALICE);
        uniqueTutorialList.setTutorial(ALICE, ALICE);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(ALICE);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasSameIdentity_success() {
        uniqueTutorialList.add(ALICE);
        Tutorial editedAlice = new TutorialBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withStudents(VALID_TAG_HUSBAND).build();
        uniqueTutorialList.setTutorial(ALICE, editedAlice);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(editedAlice);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasDifferentIdentity_success() {
        uniqueTutorialList.add(ALICE);
        uniqueTutorialList.setTutorial(ALICE, BOB);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(BOB);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasNonUniqueIdentity_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(ALICE);
        uniqueTutorialList.add(BOB);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList.setTutorial(ALICE, BOB));
    }

    @Test
    public void remove_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.remove(null));
    }

    @Test
    public void remove_tutorialDoesNotExist_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList.remove(ALICE));
    }

    @Test
    public void remove_existingTutorial_removesTutorial() {
        uniqueTutorialList.add(ALICE);
        uniqueTutorialList.remove(ALICE);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullUniqueTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((UniqueTutorialList) null));
    }

    @Test
    public void setTutorials_uniqueTutorialList_replacesOwnListWithProvidedUniqueTutorialList() {
        uniqueTutorialList.add(ALICE);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(BOB);
        uniqueTutorialList.setTutorials(expectedUniqueTutorialList);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((List<Tutorial>) null));
    }

    @Test
    public void setTutorials_list_replacesOwnListWithProvidedList() {
        uniqueTutorialList.add(ALICE);
        List<Tutorial> tutorialList = Collections.singletonList(BOB);
        uniqueTutorialList.setTutorials(tutorialList);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(BOB);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_listWithDuplicateTutorials_throwsDuplicateTutorialException() {
        List<Tutorial> listWithDuplicateTutorials = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList.setTutorials(
                listWithDuplicateTutorials));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueTutorialList.asUnmodifiableObservableList()
                .remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTutorialList.asUnmodifiableObservableList().toString(), uniqueTutorialList.toString());
    }
}
