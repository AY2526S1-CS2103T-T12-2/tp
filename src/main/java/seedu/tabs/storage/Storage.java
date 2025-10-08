package seedu.tabs.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tabs.commons.exceptions.DataLoadingException;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.ReadOnlyUserPrefs;
import seedu.tabs.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TAbsStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTAbsFilePath();

    @Override
    Optional<ReadOnlyTAbs> readTAbs() throws DataLoadingException;

    @Override
    void saveTAbs(ReadOnlyTAbs tabs) throws IOException;

}
