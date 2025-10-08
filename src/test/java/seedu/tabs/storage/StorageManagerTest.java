package seedu.tabs.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.model.TAbs;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonTAbsStorage tabsStorage = new JsonTAbsStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(tabsStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void tabsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTAbsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTAbsStorageTest} class.
         */
        TAbs original = getTypicalTAbs();
        storageManager.saveTAbs(original);
        ReadOnlyTAbs retrieved = storageManager.readTAbs().get();
        assertEquals(original, new TAbs(retrieved));
    }

    @Test
    public void getTAbsFilePath() {
        assertNotNull(storageManager.getTAbsFilePath());
    }

}
