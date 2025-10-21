package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.NON_EXISTENT_TUTORIAL_ID;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS1010_C303;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2040_C505;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_C101;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.ReadOnlyUserPrefs;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Contains unit tests for {@code AddStudentCommand}.
 */
public class AddStudentCommandTest {
    // Use CommandTestUtil constants for students
    private static final Student STUDENT_A = new Student(VALID_STUDENT_A);
    private static final Student STUDENT_B = new Student(VALID_STUDENT_B);

    /**
     * A default model stub that has all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTAbsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTAbsFilePath(Path tabsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tutorial aTutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void copyTutorial(Tutorial sourceTutorial, TutorialId newTutorialId, Date newDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTAbs(ReadOnlyTAbs newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(Tutorial aTutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorialId(TutorialId aTutorialId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTutorial(Tutorial target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorial(Tutorial target, Tutorial editedTutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that:
     * - starts with a given list of tutorials in both the backing store and the filtered list
     * - records the last setTutorial target/edited so tests can assert the result
     */
    private class ModelStubWithTutorials extends ModelStub {
        private final ObservableList<Tutorial> backing = FXCollections.observableArrayList();
        private final ObservableList<Tutorial> filtered = FXCollections.observableArrayList();
        private final TAbs tAbs = new TAbs();

        private Tutorial lastSetTarget;
        private Tutorial lastSetEdited;

        ModelStubWithTutorials(Tutorial... tutorials) {
            backing.addAll(Arrays.asList(tutorials));
            filtered.setAll(backing);
            tAbs.setTutorials(new ArrayList<>(backing));
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            return tAbs;
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return filtered;
        }

        @Override
        public void setTutorial(Tutorial target, Tutorial editedTutorial) {
            // Simulate replacing in backing + filtered lists
            int idx = -1;
            for (int i = 0; i < backing.size(); i++) {
                if (backing.get(i).equals(target)) {
                    idx = i;
                    break;
                }
            }
            if (idx >= 0) {
                backing.set(idx, editedTutorial);
            }
            int filteredIdx = -1;
            for (int i = 0; i < filtered.size(); i++) {
                if (filtered.get(i).equals(target)) {
                    filteredIdx = i;
                    break;
                }
            }
            if (filteredIdx >= 0) {
                filtered.set(filteredIdx, editedTutorial);
            }

            // Keep TAbs in sync for completeness
            tAbs.setTutorials(new ArrayList<>(backing));

            lastSetTarget = target;
            lastSetEdited = editedTutorial;
        }

        @Override
        public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
            // show-all or apply a filter; AddStudentCommand uses PREDICATE_SHOW_ALL_TUTORIALS after update
            List<Tutorial> newFiltered = new ArrayList<>();
            for (Tutorial t : backing) {
                if (predicate.test(t)) {
                    newFiltered.add(t);
                }
            }
            filtered.setAll(newFiltered);
        }

        Tutorial getLastSetTarget() {
            return lastSetTarget;
        }

        Tutorial getLastSetEdited() {
            return lastSetEdited;
        }
    }

    @Test
    public void execute_addSingleStudent_success() throws Exception {
        // Use an empty tutorial from TypicalTutorials (e.g., C505)
        Tutorial emptyTutorial = TUTORIAL_CS2040_C505;
        TutorialId emptyTutorialId = emptyTutorial.getTutorialId();

        ModelStubWithTutorials model = new ModelStubWithTutorials(emptyTutorial);
        Set<Student> toAdd = new HashSet<>(Set.of(STUDENT_A));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(emptyTutorialId.id));

        CommandResult result = cmd.execute(model);

        // sanity: model updated
        Tutorial edited = model.getLastSetEdited();
        assertEquals(emptyTutorialId, edited.getTutorialId());
        assertTrue(edited.getStudents().contains(STUDENT_A));

        // message should state success and tutorial id
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("were added to tutorial " + emptyTutorialId));
        // should not include duplicate-warning section
        assertFalse(feedback.contains("are already in tutorial"));
    }

    @Test
    public void execute_addMultipleStudentsPartialDuplicate_success() throws Exception {
        // C101 already has STUDENT_A per TypicalTutorials
        Tutorial tutorialWithA = TUTORIAL_CS2103T_C101;
        TutorialId tutorialWithAId = tutorialWithA.getTutorialId();

        ModelStubWithTutorials model = new ModelStubWithTutorials(tutorialWithA);
        Set<Student> toAdd = new HashSet<>(Set.of(STUDENT_A, STUDENT_B));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(tutorialWithAId.id));

        CommandResult result = cmd.execute(model);

        // model updated: ALICE remains, BOB added
        Tutorial edited = model.getLastSetEdited();
        assertTrue(edited.getStudents().contains(STUDENT_A));
        assertTrue(edited.getStudents().contains(STUDENT_B));

        String feedback = result.getFeedbackToUser();
        // success section present
        assertTrue(feedback.contains("were added to tutorial " + tutorialWithAId));
        // duplicate section present
        assertTrue(feedback.contains("are already in tutorial " + tutorialWithAId));
    }

    @Test
    public void execute_allDuplicates_throwsCommandException() {
        // C101 has STUDENT_A; try to add STUDENT_A only → all duplicates
        Tutorial tutorialWithA = TUTORIAL_CS2103T_C101;
        TutorialId tutorialWithAId = tutorialWithA.getTutorialId();

        ModelStubWithTutorials model = new ModelStubWithTutorials(tutorialWithA);
        Set<Student> toAdd = new HashSet<>(Set.of(STUDENT_A));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(tutorialWithAId.id));

        String expectedMsg = String.format(
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT,
                toAdd,
                tutorialWithAId);

        assertThrows(CommandException.class, expectedMsg, () -> cmd.execute(model));
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        // Model has some tutorials, but we query a non-existent id
        ModelStubWithTutorials model = new ModelStubWithTutorials(
                TUTORIAL_CS2103T_C101, TUTORIAL_CS1010_C303);
        Set<Student> toAdd = new HashSet<>(Set.of(STUDENT_B));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(NON_EXISTENT_TUTORIAL_ID));

        assertThrows(CommandException.class, Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND, () -> cmd.execute(model));
    }

    @Test
    public void equals() {
        Set<Student> setA = new HashSet<>(Set.of(STUDENT_A));
        Set<Student> setB = new HashSet<>(Set.of(STUDENT_B));

        AddStudentCommand addAtoC123 = new AddStudentCommand(
                setA, new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123));
        AddStudentCommand addAtoC123Copy = new AddStudentCommand(
                new HashSet<>(Set.of(STUDENT_A)), new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123));
        AddStudentCommand addBtoC123 = new AddStudentCommand(
                setB, new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123));
        AddStudentCommand addAtoT456 = new AddStudentCommand(
                setA, new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_T456));

        // same values -> true
        assertEquals(addAtoC123, addAtoC123Copy);
        // different student sets -> false
        assertNotEquals(addAtoC123, addBtoC123);
        // different predicates (different tutorial) -> false
        assertNotEquals(addAtoC123, addAtoT456);
        // null -> false
        assertNotEquals(null, addAtoC123);
    }
}
