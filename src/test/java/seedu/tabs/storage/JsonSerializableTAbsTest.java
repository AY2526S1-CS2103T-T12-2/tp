package seedu.tabs.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tabs.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.commons.util.JsonUtil;
import seedu.tabs.model.TAbs;
import seedu.tabs.testutil.TypicalTutorials;

public class JsonSerializableTAbsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTAbsTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalTutorialsTAbs.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidTutorialTAbs.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateTutorialTAbs.json");

    @Test
    public void toModelType_typicalTutorialsFile_success() throws Exception {
        JsonSerializableTAbs dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableTAbs.class).get();
        TAbs TAbsFromFile = dataFromFile.toModelType();
        TAbs typicalTutorialsTAbs = TypicalTutorials.getTypicalTAbs();
        assertEquals(TAbsFromFile, typicalTutorialsTAbs);
    }

    @Test
    public void toModelType_invalidTutorialFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTAbs dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableTAbs.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTutorials_throwsIllegalValueException() throws Exception {
        JsonSerializableTAbs dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableTAbs.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTAbs.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
