package seedu.tabs.model;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tabs.commons.core.GuiSettings;
import seedu.tabs.commons.core.LogsCenter;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Represents the in-memory model of the TAbs data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TAbs tabs;
    private final UserPrefs userPrefs;
    private final FilteredList<Tutorial> filteredTutorials;

    /**
     * Initializes a ModelManager with the given tabs and userPrefs.
     */
    public ModelManager(ReadOnlyTAbs tabs, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(tabs, userPrefs);

        logger.fine("Initializing with TAbs: " + tabs + " and user prefs " + userPrefs);

        this.tabs = new TAbs(tabs);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTutorials = new FilteredList<>(this.tabs.getTutorialList());
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
        this.tabs.resetData(tabs);
    }

    @Override
    public ReadOnlyTAbs getTAbs() {
        return tabs;
    }

    @Override
    public boolean hasTutorial(Tutorial aTutorial) {
        requireNonNull(aTutorial);
        return tabs.hasTutorial(aTutorial);
    }

    @Override
    public boolean hasTutorialId(TutorialId aTutorialId) {
        requireNonNull(aTutorialId);
        return tabs.getTutorialList().stream().anyMatch(t -> t.getTutorialId().equals(aTutorialId));
    }

    @Override
    public void deleteTutorial(Tutorial target) {
        tabs.removeTutorial(target);
    }

    @Override
    public void addTutorial(Tutorial aTutorial) {
        tabs.addTutorial(aTutorial);
        updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
    }

    @Override
    public void copyTutorial(Tutorial sourceTutorial, TutorialId newTutorialId, Date newDate) {
        requireAllNonNull(sourceTutorial, newTutorialId, newDate);
        // Create deep copies of students to avoid sharing mutable state (e.g., attendance)
        Set<Student> copiedStudents = sourceTutorial.getStudents().stream()
                .map(student -> new Student(student.studentId))
                .collect(Collectors.toSet());

        // Create the copied tutorial with source's module code and deep-copied students, but new ID and date
        Tutorial copiedTutorial = new Tutorial(
                newTutorialId,
                sourceTutorial.getModuleCode(),
                newDate,
                copiedStudents
        );
        tabs.addTutorial(copiedTutorial);
        updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
    }

    @Override
    public void setTutorial(Tutorial target, Tutorial editedTutorial) {
        requireAllNonNull(target, editedTutorial);

        tabs.setTutorial(target, editedTutorial);
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
        return tabs.equals(otherModelManager.tabs)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredTutorials.equals(otherModelManager.filteredTutorials);
    }

}
