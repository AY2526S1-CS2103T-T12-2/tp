package seedu.tabs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_A101;
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

    private final TAbs tabs = new TAbs();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), tabs.getTutorialList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tabs.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTAbs_replacesData() {
        TAbs newData = getTypicalTAbs();
        tabs.resetData(newData);
        assertEquals(newData, tabs);
    }

    @Test
    public void resetData_withDuplicateTutorials_throwsDuplicateTutorialException() {
        // Two tutorials with the same identity fields
        Tutorial editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withStudents(VALID_STUDENT_A)
                .build();
        List<Tutorial> newTutorials = Arrays.asList(TUTORIAL_CS2103T_A101, editedAlice);
        TAbsStub newData = new TAbsStub(newTutorials);

        assertThrows(DuplicateTutorialException.class, () -> tabs.resetData(newData));
    }

    @Test
    public void hasTutorial_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tabs.hasTutorial(null));
    }

    @Test
    public void hasTutorial_tutorialNotInTAbs_returnsFalse() {
        assertFalse(tabs.hasTutorial(TUTORIAL_CS2103T_A101));
    }

    @Test
    public void hasTutorial_tutorialInTAbs_returnsTrue() {
        tabs.addTutorial(TUTORIAL_CS2103T_A101);
        assertTrue(tabs.hasTutorial(TUTORIAL_CS2103T_A101));
    }

    @Test
    public void hasTutorial_tutorialWithSameIdentityFieldsInTAbs_returnsTrue() {
        tabs.addTutorial(TUTORIAL_CS2103T_A101);
        Tutorial editedAlice = new TutorialBuilder(TUTORIAL_CS2103T_A101).withStudents(VALID_STUDENT_A)
                .build();
        assertTrue(tabs.hasTutorial(editedAlice));
    }

    @Test
    public void getTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tabs.getTutorialList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TAbs.class.getCanonicalName() + "{tutorials=" + tabs.getTutorialList() + "}";
        assertEquals(expected, tabs.toString());
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
