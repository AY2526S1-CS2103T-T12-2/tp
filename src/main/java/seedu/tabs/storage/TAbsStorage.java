package seedu.tabs.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tabs.commons.exceptions.DataLoadingException;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.TAbs;

/**
 * Represents a storage for {@link TAbs}.
 */
public interface TAbsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTAbsFilePath();

    /**
     * Returns TAbs data as a {@link ReadOnlyTAbs}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTAbs> readTAbs() throws DataLoadingException;

    /**
     * @see #getTAbsFilePath()
     */
    Optional<ReadOnlyTAbs> readTAbs(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTAbs} to the storage.
     * @param tabs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTAbs(ReadOnlyTAbs tabs) throws IOException;

    /**
     * @see #saveTAbs(ReadOnlyTAbs)
     */
    void saveTAbs(ReadOnlyTAbs tabs, Path filePath) throws IOException;

}
