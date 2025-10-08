package seedu.tabs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.ALICE;
import static seedu.tabs.testutil.TypicalTutorials.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;
import seedu.tabs.testutil.TAbsBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TAbs(), new TAbs(modelManager.getTAbs()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTAbsFilePath(Paths.get("tabs/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTAbsFilePath(Paths.get("new/tabs/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTAbsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setTAbsFilePath(null));
    }

    @Test
    public void setTAbsFilePath_validPath_setsTAbsFilePath() {
        Path path = Paths.get("tabs/book/file/path");
        modelManager.setTAbsFilePath(path);
        assertEquals(path, modelManager.getTAbsFilePath());
    }

    @Test
    public void hasTutorial_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTutorial(null));
    }

    @Test
    public void hasTutorial_tutorialNotInTAbs_returnsFalse() {
        assertFalse(modelManager.hasTutorial(ALICE));
    }

    @Test
    public void hasTutorial_tutorialInTAbs_returnsTrue() {
        modelManager.addTutorial(ALICE);
        assertTrue(modelManager.hasTutorial(ALICE));
    }

    @Test
    public void getFilteredTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTutorialList().remove(0));
    }

    @Test
    public void equals() {
        TAbs tabs = new TAbsBuilder().withTutorial(ALICE).withTutorial(BENSON).build();
        TAbs differentTAbs = new TAbs();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(tabs, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(tabs, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different tabs -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTAbs, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getTutorialId().fullName.split("\\s+");
        modelManager.updateFilteredTutorialList(new TutorialIdContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(tabs, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTutorialList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTAbsFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(tabs, differentUserPrefs)));
    }
}
