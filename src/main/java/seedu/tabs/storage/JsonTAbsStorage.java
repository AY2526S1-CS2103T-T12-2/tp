package seedu.tabs.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tabs.commons.core.LogsCenter;
import seedu.tabs.commons.exceptions.DataLoadingException;
import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.commons.util.FileUtil;
import seedu.tabs.commons.util.JsonUtil;
import seedu.tabs.model.ReadOnlyTAbs;

/**
 * A class to access TAbs data stored as a json file on the hard disk.
 */
public class JsonTAbsStorage implements TAbsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTAbsStorage.class);

    private Path filePath;

    public JsonTAbsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTAbsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTAbs> readTAbs() throws DataLoadingException {
        return readTAbs(filePath);
    }

    /**
     * Similar to {@link #readTAbs()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyTAbs> readTAbs(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTAbs> jsonTAbs = JsonUtil.readJsonFile(
                filePath, JsonSerializableTAbs.class);
        if (!jsonTAbs.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTAbs.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTAbs(ReadOnlyTAbs tabs) throws IOException {
        saveTAbs(tabs, filePath);
    }

    /**
     * Similar to {@link #saveTAbs(ReadOnlyTAbs)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTAbs(ReadOnlyTAbs tabs, Path filePath) throws IOException {
        requireNonNull(tabs);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTAbs(tabs), filePath);
    }

}
