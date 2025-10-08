package seedu.tabs.model;

import java.nio.file.Path;

import seedu.tabs.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getTAbsFilePath();

}
