package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.ReadOnlyUserPrefs;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TutorialBuilder;

/**
 * Contains unit tests for {@code ListStudentsCommand}.
 */
public class ListStudentsCommandTest {

    // Data
    private static final TutorialId T01_ID = new TutorialId("T01");
    private static final TutorialId T02_ID_EMPTY = new TutorialId("T02");
    private static final TutorialId T99_ID_NON_EXISTENT = new TutorialId("T99");


    private static final Student ALICE = new Student("A0000001Z");
    private static final Student BOB = new Student("A0000002Z");

    private static final Tutorial T01 = new TutorialBuilder()
            .withName("T01")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents(ALICE.studentId, BOB.studentId)
            .build();

    private static final Tutorial T02_EMPTY = new TutorialBuilder()
            .withName("T02")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents() // Initializes an empty set
            .build();

    /**
     * A default model stub that has all of the methods failing.
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
     * A Model stub that contains a predefined list of tutorials, used for ListStudentsCommand.
     */
    private class ModelStubWithTutorials extends ModelStub {
        private final List<Tutorial> tutorials;
        private final TAbs tAbs;

        ModelStubWithTutorials() {
            this.tutorials = new ArrayList<>(Arrays.asList(T01, T02_EMPTY));
            this.tAbs = new TAbs();
            this.tAbs.setTutorials(this.tutorials);
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            return tAbs;
        }
    }

    @Test
    public void execute_tutorialWithStudents_success() throws CommandException {
        Model model = new ModelStubWithTutorials();
        ListStudentsCommand listStudentsCommand = new ListStudentsCommand(T01_ID);

        // The expected output format is based on the logic of ListStudentsCommand:
        // "1. [StudentID1]\n2. [StudentID2]\n"
        String successMessage = String.format("Displaying all students enrolled in tutorial %s \n", T01_ID);
        String expectedMessage = successMessage + "1. " + ALICE.studentId + "\n"
                + "2. " + BOB.studentId + "\n";

        CommandResult commandResult = listStudentsCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonExistentTutorialId_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        ListStudentsCommand listStudentsCommand = new ListStudentsCommand(T99_ID_NON_EXISTENT);

        String expectedMessage = String.format("A tutorial with the TUTORIAL_ID %s does not exist",
                T99_ID_NON_EXISTENT.fullName);

        assertThrows(CommandException.class, expectedMessage, () -> listStudentsCommand.execute(model));
    }

    @Test
    public void execute_tutorialWithNoStudents_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        ListStudentsCommand listStudentsCommand = new ListStudentsCommand(T02_ID_EMPTY);

        String expectedMessage = String.format("The tutorial %s has no students enrolled.",
                T02_ID_EMPTY.fullName);

        assertThrows(CommandException.class, expectedMessage, () -> listStudentsCommand.execute(model));
    }

    @Test
    public void equals() {
        ListStudentsCommand listT01Command = new ListStudentsCommand(T01_ID);
        ListStudentsCommand listT02Command = new ListStudentsCommand(T02_ID_EMPTY);

        // same object -> returns true
        assertTrue(listT01Command.equals(listT01Command));

        // same values -> returns true
        ListStudentsCommand listT01CommandCopy = new ListStudentsCommand(T01_ID);
        assertTrue(listT01Command.equals(listT01CommandCopy));

        // different types -> returns false
        assertFalse(listT01Command.equals(1));

        // null -> returns false
        assertFalse(listT01Command.equals(null));

        // different tutorialId -> returns false
        assertFalse(listT01Command.equals(listT02Command));
    }

    @Test
    public void toStringMethod() {
        ListStudentsCommand listStudentsCommand = new ListStudentsCommand(T01_ID);
        String expected = ListStudentsCommand.class.getCanonicalName() + "{tutorialId=" + T01_ID + "}";
        assertEquals(expected, listStudentsCommand.toString());
    }
}
