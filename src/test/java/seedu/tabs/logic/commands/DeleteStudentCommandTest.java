package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tabs.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tabs.commons.core.GuiSettings;
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

public class DeleteStudentCommandTest {
    private static final String MESSAGE_SUCCESS_TEMPLATE = "Student %1$s deleted from tutorial %2$s.";

    private static final TutorialIdMatchesKeywordPredicate T01_PREDICATE =
            new TutorialIdMatchesKeywordPredicate("T01");
    private static final TutorialIdMatchesKeywordPredicate T02_PREDICATE =
            new TutorialIdMatchesKeywordPredicate("T02");
    private static final TutorialIdMatchesKeywordPredicate T99_NON_EXISTENT_PREDICATE =
            new TutorialIdMatchesKeywordPredicate("T99");

    private static final Student ALICE = new Student("A0000001Z");
    private static final Student BOB = new Student("A0000002Z");

    private static final Tutorial T01 = new TutorialBuilder()
            .withId("T01")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents(ALICE.studentId, BOB.studentId)
            .build();

    private static final Tutorial T02_EMPTY = new TutorialBuilder()
            .withId("T02")
            .withModuleCode("CS2103T")
            .withDate("2025-10-14")
            .withStudents() // Initializes an empty set
            .build();

    /**
     * A default model stub that has all of the methods failing.
     */
    private static class ModelStub implements Model {
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
     * A Model stub that contains predefined tutorials, used for DeleteStudentCommand
     */
    private static class ModelStubWithTutorials extends ModelStub {
        private final List<Tutorial> tutorials;
        private final TAbs tAbs;

        ModelStubWithTutorials() {
            this.tutorials = new ArrayList<>(Arrays.asList(T01, T02_EMPTY));
            this.tAbs = new TAbs();
            this.tAbs.setTutorials(this.tutorials);
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return new FilteredList<>(this.tAbs.getTutorialList());
        }

        @Override
        public void setTutorial(Tutorial target, Tutorial editedTutorial) {
            requireAllNonNull(target, editedTutorial);
            tAbs.setTutorial(target, editedTutorial);
        }

        @Override
        public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
            //do nothing
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            return tAbs;
        }
    }


    @Test
    public void execute_tutorialWithStudents_success() throws CommandException {
        Model model = new ModelStubWithTutorials();
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(T01_PREDICATE, ALICE);
        String expectedMessage = String.format(MESSAGE_SUCCESS_TEMPLATE, ALICE.studentId, T01.getTutorialId());
        CommandResult commandResult = deleteStudentCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonExistentTutorial_throwsCommandException() {
        Model model = new ModelStubWithTutorials();
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(T99_NON_EXISTENT_PREDICATE, ALICE);

        String expectedMessage = "No tutorial with the provided tutorial ID was found.";

        assertThrows(CommandException.class, expectedMessage, () -> deleteStudentCommand.execute(model));
    }

    @Test
    public void equals() {
        DeleteStudentCommand listT01Command = new DeleteStudentCommand(T01_PREDICATE, ALICE);
        DeleteStudentCommand listT02Command = new DeleteStudentCommand(T02_PREDICATE, ALICE);

        // same object -> returns true
        assertEquals(listT01Command, listT01Command);

        // same values -> returns true
        DeleteStudentCommand listT01CommandCopy = new DeleteStudentCommand(T01_PREDICATE, ALICE);
        assertEquals(listT01Command, listT01CommandCopy);

        // null -> returns false
        assertNotEquals(null, listT01Command);

        // different tutorialId -> returns false
        assertNotEquals(listT01Command, listT02Command);
    }

}
