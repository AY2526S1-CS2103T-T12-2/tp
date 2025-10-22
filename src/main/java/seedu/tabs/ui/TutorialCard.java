package seedu.tabs.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable tutorialIds cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/TAbs-level4/issues/336">The issue on TAbs level 4</a>
     */

    public final Tutorial aTutorial;

    @FXML
    private HBox cardPane;
    @FXML
    private Label tutorialId;
    @FXML
    private Label id;
    @FXML
    private Label moduleCode;
    @FXML
    private Label date;
    @FXML
    private FlowPane students;

    /**
     * Creates a {@code TutorialCode} with the given {@code Tutorial} and index to display.
     */
    public TutorialCard(Tutorial aTutorial, int displayedIndex) {
        super(FXML);
        this.aTutorial = aTutorial;
        id.setText(displayedIndex + ". ");
        tutorialId.setText(aTutorial.getTutorialId().tutorialId);
        moduleCode.setText(aTutorial.getModuleCode().value);
        date.setText(aTutorial.getDate().value);
        aTutorial.getStudents().stream()
                .sorted(Comparator.comparing(student -> student.studentId))
                .forEach(student -> {
                    Label studentLabel = new Label(student.studentId);
                    studentLabel.setStyle(student.getAttendance() ? "-fx-background-color: green" : null);
                    students.getChildren().add(studentLabel);
                });
    }
}
