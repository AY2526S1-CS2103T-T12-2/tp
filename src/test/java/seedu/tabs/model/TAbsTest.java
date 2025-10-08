package seedu.tabs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.ALICE;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.tabs.testutil.TutorialBuilder;

public class TAbsTest {

    private final TAbs TAbs = new TAbs();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), TAbs.getTutorialList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TAbs.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTAbs_replacesData() {
        TAbs newData = getTypicalTAbs();
        TAbs.resetData(newData);
        assertEquals(newData, TAbs);
    }

    @Test
    public void resetData_withDuplicateTutorials_throwsDuplicateTutorialException() {
        // Two tutorials with the same identity fields
        Tutorial editedAlice = new TutorialBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withStudents(VALID_TAG_HUSBAND)
                .build();
        List<Tutorial> newTutorials = Arrays.asList(ALICE, editedAlice);
        TAbsStub newData = new TAbsStub(newTutorials);

        assertThrows(DuplicateTutorialException.class, () -> TAbs.resetData(newData));
    }

    @Test
    public void hasTutorial_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TAbs.hasTutorial(null));
    }

    @Test
    public void hasTutorial_tutorialNotInTAbs_returnsFalse() {
        assertFalse(TAbs.hasTutorial(ALICE));
    }

    @Test
    public void hasTutorial_tutorialInTAbs_returnsTrue() {
        TAbs.addTutorial(ALICE);
        assertTrue(TAbs.hasTutorial(ALICE));
    }

    @Test
    public void hasTutorial_tutorialWithSameIdentityFieldsInTAbs_returnsTrue() {
        TAbs.addTutorial(ALICE);
        Tutorial editedAlice = new TutorialBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withStudents(VALID_TAG_HUSBAND)
                .build();
        assertTrue(TAbs.hasTutorial(editedAlice));
    }

    @Test
    public void getTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> TAbs.getTutorialList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TAbs.class.getCanonicalName() + "{tutorials=" + TAbs.getTutorialList() + "}";
        assertEquals(expected, TAbs.toString());
    }

    /**
     * A stub ReadOnlyTAbs whose tutorials list can violate interface constraints.
     */
    private static class TAbsStub implements ReadOnlyTAbs {
        private final ObservableList<Tutorial> aTutorials = FXCollections.observableArrayList();

        TAbsStub(Collection<Tutorial> aTutorials) {
            this.aTutorials.setAll(aTutorials);
        }

        @Override
        public ObservableList<Tutorial> getTutorialList() {
            return aTutorials;
        }
    }

}
