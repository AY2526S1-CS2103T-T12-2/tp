package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents the in-memory model of the TAbs data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TAbs TAbs;
    private final UserPrefs userPrefs;
    private final FilteredList<Tutorial> filteredTutorials;

    /**
     * Initializes a ModelManager with the given tabs and userPrefs.
     */
    public ModelManager(ReadOnlyTAbs tabs, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(tabs, userPrefs);

        logger.fine("Initializing with TAbs: " + tabs + " and user prefs " + userPrefs);

        this.TAbs = new TAbs(tabs);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTutorials = new FilteredList<>(this.TAbs.getTutorialList());
    }

    public ModelManager() {
        this(new TAbs(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTAbsFilePath() {
        return userPrefs.getTAbsFilePath();
    }

    @Override
    public void setTAbsFilePath(Path tabsFilePath) {
        requireNonNull(tabsFilePath);
        userPrefs.setTAbsFilePath(tabsFilePath);
    }

    //=========== TAbs ================================================================================

    @Override
    public void setTAbs(ReadOnlyTAbs tabs) {
        this.TAbs.resetData(tabs);
    }

    @Override
    public ReadOnlyTAbs getTAbs() {
        return TAbs;
    }

    @Override
    public boolean hasTutorial(Tutorial aTutorial) {
        requireNonNull(aTutorial);
        return TAbs.hasTutorial(aTutorial);
    }

    @Override
    public void deleteTutorial(Tutorial target) {
        TAbs.removeTutorial(target);
    }

    @Override
    public void addTutorial(Tutorial aTutorial) {
        TAbs.addTutorial(aTutorial);
        updateFilteredTutorialList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireAllNonNull(target, editedTutorial);

        TAbs.setTutorial(target, editedTutorial);
    }

    //=========== Filtered Tutorial List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tutorial} backed by the internal list of
     * {@code versionedTAbs}
     */
    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return filteredTutorials;
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        filteredTutorials.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return TAbs.equals(otherModelManager.TAbs)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredTutorials.equals(otherModelManager.filteredTutorials);
    }

}
