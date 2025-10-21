package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_C101;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_MA1521_T202;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.ReadOnlyUserPrefs;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

public class CopyTutorialCommandTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        TutorialId validNewId = new TutorialId("C2");
        TutorialId validSourceId = new TutorialId("C1");
        Date validDate = new Date("2025-03-10");

        // null newTutorialId
        assertThrows(NullPointerException.class, () ->
                new CopyTutorialCommand(null, validSourceId, validDate));

        // null sourceTutorialId
        assertThrows(NullPointerException.class, () ->
                new CopyTutorialCommand(validNewId, null, validDate));

        // null date
        assertThrows(NullPointerException.class, () ->
                new CopyTutorialCommand(validNewId, validSourceId, null));
    }

    @Test
    public void execute_tutorialCopiedSuccessfully_success() throws Exception {
        ModelStubWithTutorial modelStub = new ModelStubWithTutorial(TUTORIAL_CS2103T_C101);

        TutorialId newTutorialId = new TutorialId("C2");
        TutorialId sourceTutorialId = new TutorialId("C101");
        Date newDate = new Date("2025-03-10");

        CopyTutorialCommand copyCommand = new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate);
        CommandResult commandResult = copyCommand.execute(modelStub);

        assertEquals(String.format(CopyTutorialCommand.MESSAGE_SUCCESS, "C2", "CS2103T", "C101"),
                commandResult.getFeedbackToUser());
        assertTrue(modelStub.copyCalled);
    }

    @Test
    public void execute_duplicateTutorialId_throwsCommandException() {
        ModelStubWithMultipleTutorials modelStub = new ModelStubWithMultipleTutorials(
                TUTORIAL_CS2103T_C101, TUTORIAL_MA1521_T202);

        TutorialId newTutorialId = new TutorialId("T202"); // Already exists
        TutorialId sourceTutorialId = new TutorialId("C101");
        Date newDate = new Date("2025-03-10");

        CopyTutorialCommand copyCommand = new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate);

        assertThrows(CommandException.class,
                String.format(CopyTutorialCommand.MESSAGE_DUPLICATE_TUTORIAL, "T202"), () ->
                        copyCommand.execute(modelStub));
    }

    @Test
    public void execute_sourceTutorialNotFound_throwsCommandException() {
        ModelStubWithTutorial modelStub = new ModelStubWithTutorial(TUTORIAL_CS2103T_C101);

        TutorialId newTutorialId = new TutorialId("C2");
        TutorialId sourceTutorialId = new TutorialId("C99"); // Non-existent
        Date newDate = new Date("2025-03-10");

        CopyTutorialCommand copyCommand = new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate);

        assertThrows(CommandException.class,
                String.format(CopyTutorialCommand.MESSAGE_TUTORIAL_NOT_FOUND, "C99"), () ->
                        copyCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        TutorialId newIdC2 = new TutorialId("C2");
        TutorialId newIdC3 = new TutorialId("C3");
        TutorialId sourceIdC1 = new TutorialId("C1");
        TutorialId sourceIdT1 = new TutorialId("T1");
        Date date1 = new Date("2025-03-10");
        Date date2 = new Date("2025-04-15");

        CopyTutorialCommand copyC2FromC1 = new CopyTutorialCommand(newIdC2, sourceIdC1, date1);
        CopyTutorialCommand copyC3FromC1 = new CopyTutorialCommand(newIdC3, sourceIdC1, date1);
        CopyTutorialCommand copyC2FromT1 = new CopyTutorialCommand(newIdC2, sourceIdT1, date1);
        CopyTutorialCommand copyC2FromC1DifferentDate = new CopyTutorialCommand(newIdC2, sourceIdC1, date2);

        // same object -> returns true
        assertTrue(copyC2FromC1.equals(copyC2FromC1));

        // same values -> returns true
        CopyTutorialCommand copyC2FromC1Copy = new CopyTutorialCommand(newIdC2, sourceIdC1, date1);
        assertTrue(copyC2FromC1.equals(copyC2FromC1Copy));

        // different types -> returns false
        assertFalse(copyC2FromC1.equals(1));

        // null -> returns false
        assertFalse(copyC2FromC1.equals(null));

        // different new tutorial ID -> returns false
        assertFalse(copyC2FromC1.equals(copyC3FromC1));

        // different source tutorial ID -> returns false
        assertFalse(copyC2FromC1.equals(copyC2FromT1));

        // different date -> returns false
        assertFalse(copyC2FromC1.equals(copyC2FromC1DifferentDate));
    }

    @Test
    public void toStringMethod() {
        TutorialId newId = new TutorialId("C2");
        TutorialId sourceId = new TutorialId("C1");
        Date date = new Date("2025-03-10");
        CopyTutorialCommand copyCommand = new CopyTutorialCommand(newId, sourceId, date);

        String expected = CopyTutorialCommand.class.getCanonicalName()
                + "{newTutorialId=" + newId
                + ", sourceTutorialId=" + sourceId
                + ", newDate=" + date + "}";
        assertEquals(expected, copyCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
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
     * A Model stub that contains a single tutorial and tracks copyTutorial calls.
     */
    private class ModelStubWithTutorial extends ModelStub {
        private final Tutorial tutorial;
        private boolean copyCalled = false;

        ModelStubWithTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            this.tutorial = tutorial;
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return FXCollections.observableArrayList(tutorial);
        }

        @Override
        public boolean hasTutorial(Tutorial aTutorial) {
            requireNonNull(aTutorial);
            return this.tutorial.isSameTutorial(aTutorial);
        }

        @Override
        public void copyTutorial(Tutorial sourceTutorial, TutorialId newTutorialId, Date newDate) {
            requireNonNull(sourceTutorial);
            requireNonNull(newTutorialId);
            requireNonNull(newDate);
            copyCalled = true;
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            TAbs tabs = new TAbs();
            tabs.addTutorial(tutorial);
            return tabs;
        }
    }

    /**
     * A Model stub that contains multiple tutorials.
     */
    private class ModelStubWithMultipleTutorials extends ModelStub {
        private final ObservableList<Tutorial> tutorials = FXCollections.observableArrayList();

        ModelStubWithMultipleTutorials(Tutorial... tutorials) {
            this.tutorials.addAll(tutorials);
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return tutorials;
        }

        @Override
        public boolean hasTutorial(Tutorial aTutorial) {
            requireNonNull(aTutorial);
            return tutorials.stream().anyMatch(aTutorial::isSameTutorial);
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            TAbs tabs = new TAbs();
            for (Tutorial tutorial : tutorials) {
                tabs.addTutorial(tutorial);
            }
            return tabs;
        }
    }
}
