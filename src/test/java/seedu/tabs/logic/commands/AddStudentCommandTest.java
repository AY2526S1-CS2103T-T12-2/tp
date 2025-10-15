package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;

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
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;
import seedu.tabs.testutil.TutorialBuilder;

/**
 * Contains unit tests for {@code AddStudentCommand}.
 */
public class AddStudentCommandTest {

    // Data
    private static final TutorialId T01_ID = new TutorialId("T01");
    private static final TutorialId T02_ID = new TutorialId("T02");
    private static final TutorialId T99_ID_NON_EXISTENT = new TutorialId("T99");

    private static final Student ALICE = new Student("A0000001Z");
    private static final Student BOB = new Student("A0000002Z");
    private static final Student CARL = new Student("A0000003Z");

    private static final Tutorial T01_WITH_ALICE = new TutorialBuilder()
            .withName("T01")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents(ALICE.studentId)
            .build();

    private static final Tutorial T02_EMPTY = new TutorialBuilder()
            .withName("T02")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents()
            .build();

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
        ModelStubWithTutorials model = new ModelStubWithTutorials(T02_EMPTY);
        Set<Student> toAdd = new HashSet<>(Set.of(ALICE));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(T02_ID.fullName));

        CommandResult result = cmd.execute(model);

        // sanity: model updated
        Tutorial edited = model.getLastSetEdited();
        assertEquals(T02_ID, edited.getTutorialId());
        assertTrue(edited.getStudents().contains(ALICE));

        // message should state success and tutorial id
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("were added to tutorial " + T02_ID));
        // should not include duplicate-warning section
        assertFalse(feedback.contains("are already in tutorial"));
    }

    @Test
    public void execute_addMultipleStudentsPartialDuplicate_success() throws Exception {
        // T01 already has ALICE; we add ALICE (dup) + BOB (new)
        ModelStubWithTutorials model = new ModelStubWithTutorials(T01_WITH_ALICE);
        Set<Student> toAdd = new HashSet<>(Set.of(ALICE, BOB));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(T01_ID.fullName));

        CommandResult result = cmd.execute(model);

        // model updated: ALICE remains, BOB added
        Tutorial edited = model.getLastSetEdited();
        assertTrue(edited.getStudents().contains(ALICE));
        assertTrue(edited.getStudents().contains(BOB));

        String feedback = result.getFeedbackToUser();
        // success section present
        assertTrue(feedback.contains("were added to tutorial " + T01_ID));
        // duplicate section present
        assertTrue(feedback.contains("are already in tutorial " + T01_ID));
    }

    @Test
    public void execute_allDuplicates_throwsCommandException() {
        // T01 has ALICE; try to add ALICE only → all duplicates
        ModelStubWithTutorials model = new ModelStubWithTutorials(T01_WITH_ALICE);
        Set<Student> toAdd = new HashSet<>(Set.of(ALICE));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(T01_ID.fullName));

        String expectedMsg = String.format(
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT,
                toAdd,
                T01_ID);

        assertThrows(CommandException.class, expectedMsg, () -> cmd.execute(model));
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        ModelStubWithTutorials model = new ModelStubWithTutorials(T01_WITH_ALICE, T02_EMPTY);
        Set<Student> toAdd = new HashSet<>(Set.of(CARL));
        AddStudentCommand cmd = new AddStudentCommand(
                toAdd,
                new TutorialIdMatchesKeywordPredicate(T99_ID_NON_EXISTENT.fullName));

        assertThrows(CommandException.class, Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND, () -> cmd.execute(model));
    }

    @Test
    public void equals() {
        Set<Student> setAlice = new HashSet<>(Set.of(ALICE));
        Set<Student> setBob = new HashSet<>(Set.of(BOB));

        AddStudentCommand addAliceT01 = new AddStudentCommand(
                setAlice, new TutorialIdMatchesKeywordPredicate(T01_ID.fullName));
        AddStudentCommand addAliceT01Copy = new AddStudentCommand(
                new HashSet<>(Set.of(ALICE)), new TutorialIdMatchesKeywordPredicate(T01_ID.fullName));
        AddStudentCommand addBobT01 = new AddStudentCommand(
                setBob, new TutorialIdMatchesKeywordPredicate(T01_ID.fullName));
        AddStudentCommand addAliceT02 = new AddStudentCommand(
                setAlice, new TutorialIdMatchesKeywordPredicate(T02_ID.fullName));

        // same values -> true
        assertEquals(addAliceT01, addAliceT01Copy);
        // different student sets -> false
        assertNotEquals(addAliceT01, addBobT01);
        // different predicates (different tutorial) -> false
        assertNotEquals(addAliceT01, addAliceT02);
        // null -> false
        assertNotEquals(null, addAliceT01);
    }
}
