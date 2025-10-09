package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_C101;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.testutil.TutorialBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTutorialCommand(null));
    }

    @Test
    public void execute_tutorialAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTutorialAdded modelStub = new ModelStubAcceptingTutorialAdded();
        Tutorial validTutorial = new TutorialBuilder().build();

        CommandResult commandResult = new AddTutorialCommand(validTutorial).execute(modelStub);

        assertEquals(String.format(AddTutorialCommand.MESSAGE_SUCCESS, Messages.format(validTutorial)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTutorial), modelStub.tutorialsAdded);
    }

    @Test
    public void execute_duplicateTutorial_throwsCommandException() {
        Tutorial validTutorial = new TutorialBuilder().build();
        AddTutorialCommand addCommand = new AddTutorialCommand(validTutorial);
        ModelStub modelStub = new ModelStubWithTutorial(validTutorial);

        assertThrows(CommandException.class, AddTutorialCommand.MESSAGE_DUPLICATE_TUTORIAL, (
                ) -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Tutorial alice = new TutorialBuilder().withName("C101").build();
        Tutorial bob = new TutorialBuilder().withName("T202").build();
        AddTutorialCommand addAliceCommand = new AddTutorialCommand(alice);
        AddTutorialCommand addBobCommand = new AddTutorialCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddTutorialCommand addAliceCommandCopy = new AddTutorialCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different tutorial -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddTutorialCommand addCommand = new AddTutorialCommand(TUTORIAL_CS2103T_C101);
        String expected = AddTutorialCommand.class.getCanonicalName() + "{toAdd=" + TUTORIAL_CS2103T_C101 + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
     * A Model stub that contains a single tutorial.
     */
    private class ModelStubWithTutorial extends ModelStub {
        private final Tutorial aTutorial;

        ModelStubWithTutorial(Tutorial aTutorial) {
            requireNonNull(aTutorial);
            this.aTutorial = aTutorial;
        }

        @Override
        public boolean hasTutorial(Tutorial aTutorial) {
            requireNonNull(aTutorial);
            return this.aTutorial.isSameTutorial(aTutorial);
        }
    }

    /**
     * A Model stub that always accept the tutorial being added.
     */
    private class ModelStubAcceptingTutorialAdded extends ModelStub {
        final ArrayList<Tutorial> tutorialsAdded = new ArrayList<>();

        @Override
        public boolean hasTutorial(Tutorial aTutorial) {
            requireNonNull(aTutorial);
            return tutorialsAdded.stream().anyMatch(aTutorial::isSameTutorial);
        }

        @Override
        public void addTutorial(Tutorial aTutorial) {
            requireNonNull(aTutorial);
            tutorialsAdded.add(aTutorial);
        }

        @Override
        public ReadOnlyTAbs getTAbs() {
            return new TAbs();
        }
    }

}
