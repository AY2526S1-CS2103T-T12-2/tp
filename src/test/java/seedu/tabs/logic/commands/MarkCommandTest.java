package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import seedu.tabs.testutil.TutorialBuilder;

/**
 * Contains unit tests for {@code MarkCommand}.
 */
public class MarkCommandTest {

    private static final TutorialId T01_ID = new TutorialId("T01");
    private static final Student ALICE = new Student("A0000001Z");
    private static final Student BOB = new Student("A0000002Z");
    private static final Student CHARLIE = new Student("A0000003Z");

    private static final Tutorial T01_WITH_STUDENTS = new TutorialBuilder()
            .withId("T01")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents(ALICE.studentId, BOB.studentId)
            .build();

    private static final Tutorial T02_EMPTY = new TutorialBuilder()
            .withId("T02")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents()
            .build();

    /**
     * A default model stub that has all methods throwing by default.
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

        @Override
        public GuiSettings getGuiSettings() {
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
     * Model stub that contains predefined tutorials (T01 with students, T02 empty).
     */
    private class ModelStubWithTutorials extends ModelStub {
        private final List<Tutorial> tutorials;
        private final TAbs tAbs;

        ModelStubWithTutorials() {
            this.tutorials = new ArrayList<>(Arrays.asList(T01_WITH_STUDENTS, T02_EMPTY));
            this.tAbs = new TAbs();
            this.tAbs.setTutorials(this.tutorials);
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            return tAbs;
        }

        @Override
        public void setTutorial(Tutorial target, Tutorial editedTutorial) {
            // track updates
        }

        @Override
        public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
            // No-op
        }

    }

    @Test
    public void execute_validStudents_success() throws Exception {
        Model model = new ModelStubWithTutorials();
        Set<Student> studentsToMark = new HashSet<>(Arrays.asList(ALICE));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        MarkCommand markCommand = new MarkCommand(studentsToMark, predicate);

        CommandResult result = markCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains("marked as present"));
    }

    @Test
    public void execute_validMultipleStudents_success() throws Exception {
        Model model = new ModelStubWithTutorials();
        Set<Student> studentsToMark = new HashSet<>(Arrays.asList(ALICE, BOB));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        MarkCommand markCommand = new MarkCommand(studentsToMark, predicate);

        CommandResult result = markCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains("marked as present"));
    }

    @Test
    public void execute_studentNotInTutorial_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        Set<Student> nonExistent = new HashSet<>(Arrays.asList(CHARLIE));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        MarkCommand markCommand = new MarkCommand(nonExistent, predicate);

        String expectedMessage = String.format(
                MarkCommand.MESSAGE_NOT_EXISTS,
                nonExistent,
                T01_ID
        );

        assertThrows(CommandException.class, expectedMessage, () -> markCommand.execute(model));
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        Set<Student> students = new HashSet<>(Arrays.asList(ALICE));
        TutorialIdMatchesKeywordPredicate invalidPredicate = new TutorialIdMatchesKeywordPredicate("T99");
        MarkCommand markCommand = new MarkCommand(students, invalidPredicate);

        assertThrows(CommandException.class,
                Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND, () -> markCommand.execute(model));
    }

    @Test
    public void equals() {
        Set<Student> studentsA = new HashSet<>(Arrays.asList(ALICE));
        Set<Student> studentsB = new HashSet<>(Arrays.asList(BOB));
        TutorialIdMatchesKeywordPredicate predicateT1 = new TutorialIdMatchesKeywordPredicate("T01");
        TutorialIdMatchesKeywordPredicate predicateT2 = new TutorialIdMatchesKeywordPredicate("T02");

        MarkCommand markTut1StudentA = new MarkCommand(studentsA, predicateT1);
        MarkCommand markTut1StudentACopy = new MarkCommand(studentsA, predicateT1);
        MarkCommand markTut1StudentB = new MarkCommand(studentsB, predicateT1);
        MarkCommand markTut2StudentA = new MarkCommand(studentsA, predicateT2);

        // same object -> true
        assertTrue(markTut1StudentA.equals(markTut1StudentA));

        // same values -> true
        assertTrue(markTut1StudentA.equals(markTut1StudentACopy));

        // different student sets -> false
        assertFalse(markTut1StudentA.equals(markTut1StudentB));

        // different predicates -> false
        assertFalse(markTut1StudentA.equals(markTut2StudentA));

        // null -> false
        assertFalse(markTut1StudentA.equals(null));

        // different type -> false
        assertFalse(markTut1StudentA.equals(1));
    }

    @Test
    public void toStringMethod() {
        Set<Student> students = new HashSet<>(Arrays.asList(ALICE, BOB));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        MarkCommand markCommand = new MarkCommand(students, predicate);

        // Since MarkCommand doesn’t override toString(), just check class name consistency
        assertTrue(markCommand.toString().contains("MarkCommand"));
    }
}
