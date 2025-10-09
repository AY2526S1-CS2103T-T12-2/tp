package seedu.tabs.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS2103T_C101;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_CS3230_C909;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_GE1401_T808;
import static seedu.tabs.testutil.TypicalTutorials.getTypicalTAbs;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tabs.commons.exceptions.DataLoadingException;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.TAbs;

public class JsonTAbsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTAbsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTAbs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTAbs(null));
    }

    private java.util.Optional<ReadOnlyTAbs> readTAbs(String filePath) throws Exception {
        return new JsonTAbsStorage(Paths.get(filePath)).readTAbs(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTAbs("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTAbs("notJsonFormatTAbs.json"));
    }

    @Test
    public void readTAbs_invalidTutorialTAbs_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTAbs("invalidTutorialTAbs.json"));
    }

    @Test
    public void readTAbs_invalidAndValidTutorialTAbs_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTAbs("invalidAndValidTutorialTAbs.json"));
    }

    @Test
    public void readAndSaveTAbs_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTAbs.json");
        TAbs original = getTypicalTAbs();
        JsonTAbsStorage jsonTAbsStorage = new JsonTAbsStorage(filePath);

        // Save in new file and read back
        jsonTAbsStorage.saveTAbs(original, filePath);
        ReadOnlyTAbs readBack = jsonTAbsStorage.readTAbs(filePath).get();
        assertEquals(original, new TAbs(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTutorial(TUTORIAL_GE1401_T808);
        original.removeTutorial(TUTORIAL_CS2103T_C101);
        jsonTAbsStorage.saveTAbs(original, filePath);
        readBack = jsonTAbsStorage.readTAbs(filePath).get();
        assertEquals(original, new TAbs(readBack));

        // Save and read without specifying file path
        original.addTutorial(TUTORIAL_CS3230_C909);
        jsonTAbsStorage.saveTAbs(original); // file path not specified
        readBack = jsonTAbsStorage.readTAbs().get(); // file path not specified
        assertEquals(original, new TAbs(readBack));

    }

    @Test
    public void saveTAbs_nullTAbs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTAbs(null, "SomeFile.json"));
    }

    /**
     * Saves {@code tabs} at the specified {@code filePath}.
     */
    private void saveTAbs(ReadOnlyTAbs tabs, String filePath) {
        try {
            new JsonTAbsStorage(Paths.get(filePath))
                    .saveTAbs(tabs, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTAbs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTAbs(new TAbs(), null));
    }
}
