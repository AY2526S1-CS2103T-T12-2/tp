package seedu.tabs.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Tutorial> PREDICATE_SHOW_ALL_TUTORIALS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' TAbs file path.
     */
    Path getTAbsFilePath();

    /**
     * Sets the user prefs' TAbs file path.
     */
    void setTAbsFilePath(Path tabsFilePath);

    /**
     * Replaces TAbs data with the data in {@code tabs}.
     */
    void setTAbs(ReadOnlyTAbs tabs);

    /** Returns the TAbs */
    ReadOnlyTAbs getTAbs();

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the TAbs.
     */
    boolean hasTutorial(Tutorial aTutorial);

    /**
     * Returns true if a tutorial with an ID of {@code aTutorialId} exists in TAbs.
     */
    boolean hasTutorialId(TutorialId aTutorialId);

    /**
     * Deletes the given tutorial.
     * The tutorial must exist in the TAbs.
     */
    void deleteTutorial(Tutorial target);

    /**
     * Adds the given tutorial.
     * {@code tutorial} must not already exist in the TAbs.
     */
    void addTutorial(Tutorial aTutorial);

    /**
     * Copies a tutorial from source to create a new tutorial.
     * {@code sourceTutorial} must exist in the TAbs.
     * A new tutorial with {@code newTutorialId} must not already exist in the TAbs.
     * The new tutorial will have the same module code and students as the source,
     * but with the specified new ID and date.
     */
    void copyTutorial(Tutorial sourceTutorial, TutorialId newTutorialId, Date newDate);

    /**
     * Replaces the given tutorial {@code target} with {@code editedTutorial}.
     * {@code target} must exist in the TAbs.
     * The tutorial identity of {@code editedTutorial} must not be the same as another existing tutorial in the TAbs.
     */
    void setTutorial(Tutorial target, Tutorial editedTutorial);

    /** Returns an unmodifiable view of the filtered tutorial list */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Updates the filter of the filtered tutorial list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);
}
