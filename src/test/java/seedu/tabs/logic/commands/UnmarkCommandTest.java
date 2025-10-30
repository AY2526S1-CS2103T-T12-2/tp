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
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;
import seedu.tabs.testutil.TutorialBuilder;

/**
 * Contains unit tests for {@code UnmarkCommand}.
 */
public class UnmarkCommandTest {

    private final TutorialId t1Id = new TutorialId("T01");
    private final Student alice = new Student("A0000001Z");
    private final Student bob = new Student("A0000002Z");
    private final Student charlie = new Student("A0000003Z");

    UnmarkCommandTest() {
        alice.mark();
        bob.mark();
    }

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
     * Model stub that contains predefined tutorials (T01 with marked students, T02 empty).
     */
    private class ModelStubWithTutorials extends ModelStub {
        private final List<Tutorial> tutorials;
        private final TAbs tAbs;
        private final Set<Student> markedStudents = new HashSet<>();
        private final Tutorial t2Empty = new TutorialBuilder()
                .withId("T02")
                .withModuleCode("CS2103T")
                .withDate("2025-10-14")
                .withStudents()
                .build();

        ModelStubWithTutorials() {
            Student alice = new Student("A0000001Z");
            Student bob = new Student("A0000002Z");
            alice.mark();
            alice.mark();
            markedStudents.add(alice);
            markedStudents.add(bob);
            Tutorial t1WithMarkedStudents = new Tutorial(
                    new TutorialId("T01"),
                    new ModuleCode("CS2103T"),
                    new Date("2025-10-14"),
                    markedStudents);
            this.tutorials = new ArrayList<>(Arrays.asList(t1WithMarkedStudents, t2Empty));
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
        Set<Student> studentsToUnmark = new HashSet<>(Arrays.asList(alice));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        UnmarkCommand unmarkCommand = new UnmarkCommand(studentsToUnmark, predicate);

        CommandResult result = unmarkCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains("were unmarked in tutorial T01"));
    }

    @Test
    public void execute_studentNotInTutorial_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        Set<Student> nonExistent = new HashSet<>(Arrays.asList(charlie));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        UnmarkCommand unmarkCommand = new UnmarkCommand(nonExistent, predicate);

        String expectedMessage = String.format(
                UnmarkCommand.MESSAGE_NOT_EXISTS,
                nonExistent,
                t1Id
        );

        assertThrows(CommandException.class, expectedMessage, () -> unmarkCommand.execute(model));
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        Set<Student> students = new HashSet<>(Arrays.asList(alice));
        TutorialIdMatchesKeywordPredicate invalidPredicate = new TutorialIdMatchesKeywordPredicate("T99");
        UnmarkCommand unmarkCommand = new UnmarkCommand(students, invalidPredicate);

        assertThrows(CommandException.class,
                Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND, () -> unmarkCommand.execute(model));
    }

    @Test
    public void equals() {
        Set<Student> studentsA = new HashSet<>(Arrays.asList(alice));
        Set<Student> studentsB = new HashSet<>(Arrays.asList(bob));
        TutorialIdMatchesKeywordPredicate predicateT01 = new TutorialIdMatchesKeywordPredicate("T01");
        TutorialIdMatchesKeywordPredicate predicateT02 = new TutorialIdMatchesKeywordPredicate("T02");

        UnmarkCommand markTut01StudentA = new UnmarkCommand(studentsA, predicateT01);
        UnmarkCommand markTut01StudentACopy = new UnmarkCommand(studentsA, predicateT01);
        UnmarkCommand markTut01StudentB = new UnmarkCommand(studentsB, predicateT01);
        UnmarkCommand markTut02StudentA = new UnmarkCommand(studentsA, predicateT02);

        // same object -> true
        assertTrue(markTut01StudentA.equals(markTut01StudentA));

        // same values -> true
        assertTrue(markTut01StudentA.equals(markTut01StudentACopy));

        // different student sets -> false
        assertFalse(markTut01StudentA.equals(markTut01StudentB));

        // different predicates -> false
        assertFalse(markTut01StudentA.equals(markTut02StudentA));

        // null -> false
        assertFalse(markTut01StudentA.equals(null));

        // different type -> false
        assertFalse(markTut01StudentA.equals(1));
    }

    @Test
    public void toStringMethod() {
        Set<Student> students = new HashSet<>(Arrays.asList(alice, bob));
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate("T01");
        UnmarkCommand unmarkCommand = new UnmarkCommand(students, predicate);

        // Since UnmarkCommand doesn’t override toString(), just check class name consistency
        assertTrue(unmarkCommand.toString().contains("UnmarkCommand"));
    }
}
