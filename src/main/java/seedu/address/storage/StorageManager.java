package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyTAbs;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TAbs data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TAbsStorage tabsStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TAbsStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TAbsStorage tabsStorage, UserPrefsStorage userPrefsStorage) {
        this.tabsStorage = tabsStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TAbs methods ==============================

    @Override
    public Path getTAbsFilePath() {
        return tabsStorage.getTAbsFilePath();
    }

    @Override
    public Optional<ReadOnlyTAbs> readTAbs() throws DataLoadingException {
        return readTAbs(tabsStorage.getTAbsFilePath());
    }

    @Override
    public Optional<ReadOnlyTAbs> readTAbs(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tabsStorage.readTAbs(filePath);
    }

    @Override
    public void saveTAbs(ReadOnlyTAbs tabs) throws IOException {
        saveTAbs(tabs, tabsStorage.getTAbsFilePath());
    }

    @Override
    public void saveTAbs(ReadOnlyTAbs tabs, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tabsStorage.saveTAbs(tabs, filePath);
    }

}
