package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_A101;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_TEST_T456;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tabs.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.tabs.model.tutorial.exceptions.TutorialNotFoundException;
import seedu.tabs.testutil.TutorialBuilder;
import seedu.tabs.testutil.TypicalTutorials;

public class UniqueTutorialListTest {

    private final UniqueTutorialList uniqueTutorialList = new UniqueTutorialList();

    @Test
    public void contains_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.contains(null));
    }

    @Test
    public void contains_tutorialNotInList_returnsFalse() {
        assertFalse(uniqueTutorialList.contains(TUTORIAL_CS2103T_A101));
    }

    @Test
    public void contains_tutorialInList_returnsTrue() {
        uniqueTutorialList.add(TUTORIAL_CS2103T_A101);
        assertTrue(uniqueTutorialList.contains(TUTORIAL_CS2103T_A101));
    }

    @Test
    public void contains_tutorialWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTutorialList.add(TUTORIAL_CS2103T_A101);
        Tutorial editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101)
                .withStudents(VALID_STUDENT_A).build();
        assertTrue(uniqueTutorialList.contains(editedAlice));
    }

    @Test
    public void add_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.add(null));
    }

    @Test
    public void add_duplicateTutorial_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList
                .add(TypicalTutorials.TUTORIAL_CS2103T_A101));
    }

    @Test
    public void setTutorial_nullTargetTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList
                .setTutorial(null, TypicalTutorials.TUTORIAL_CS2103T_A101));
    }

    @Test
    public void setTutorial_nullEditedTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList
                .setTutorial(TypicalTutorials.TUTORIAL_CS2103T_A101, null));
    }

    @Test
    public void setTutorial_targetTutorialNotInList_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList
                .setTutorial(TypicalTutorials.TUTORIAL_CS2103T_A101, TypicalTutorials.TUTORIAL_CS2103T_A101));
    }

    @Test
    public void setTutorial_editedTutorialIsSameTutorial_success() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        uniqueTutorialList.setTutorial(TypicalTutorials.TUTORIAL_CS2103T_A101, TypicalTutorials.TUTORIAL_CS2103T_A101);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasSameIdentity_success() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        Tutorial editedAlice = new TutorialBuilder(TypicalTutorials.TUTORIAL_CS2103T_A101)
                .withStudents(VALID_STUDENT_A).build();
        uniqueTutorialList.setTutorial(TypicalTutorials.TUTORIAL_CS2103T_A101, editedAlice);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(editedAlice);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasDifferentIdentity_success() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        uniqueTutorialList.setTutorial(TypicalTutorials.TUTORIAL_CS2103T_A101, TUTORIAL_TEST_T456);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(TUTORIAL_TEST_T456);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasNonUniqueIdentity_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        uniqueTutorialList.add(TUTORIAL_TEST_T456);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList
                .setTutorial(TypicalTutorials.TUTORIAL_CS2103T_A101, TUTORIAL_TEST_T456));
    }

    @Test
    public void remove_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.remove(null));
    }

    @Test
    public void remove_tutorialDoesNotExist_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList
                .remove(TypicalTutorials.TUTORIAL_CS2103T_A101));
    }

    @Test
    public void remove_existingTutorial_removesTutorial() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        uniqueTutorialList.remove(TypicalTutorials.TUTORIAL_CS2103T_A101);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullUniqueTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((UniqueTutorialList) null));
    }

    @Test
    public void setTutorials_uniqueTutorialList_replacesOwnListWithProvidedUniqueTutorialList() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(TUTORIAL_TEST_T456);
        uniqueTutorialList.setTutorials(expectedUniqueTutorialList);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((List<Tutorial>) null));
    }

    @Test
    public void setTutorials_list_replacesOwnListWithProvidedList() {
        uniqueTutorialList.add(TypicalTutorials.TUTORIAL_CS2103T_A101);
        List<Tutorial> tutorialList = Collections.singletonList(TUTORIAL_TEST_T456);
        uniqueTutorialList.setTutorials(tutorialList);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(TUTORIAL_TEST_T456);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_listWithDuplicateTutorials_throwsDuplicateTutorialException() {
        List<Tutorial> listWithDuplicateTutorials = Arrays.asList(TypicalTutorials.TUTORIAL_CS2103T_A101,
                TypicalTutorials.TUTORIAL_CS2103T_A101);
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
