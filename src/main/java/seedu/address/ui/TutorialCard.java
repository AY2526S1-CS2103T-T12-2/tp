package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tutorial.Tutorial;

/**
 * An UI component that displays information of a {@code Tutorial}.
 */
public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/TAbs-level4/issues/336">The issue on TAbs level 4</a>
     */

    public final Tutorial aTutorial;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label moduleCode;
    @FXML
    private Label address;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code TutorialCode} with the given {@code Tutorial} and index to display.
     */
    public TutorialCard(Tutorial aTutorial, int displayedIndex) {
        super(FXML);
        this.aTutorial = aTutorial;
        id.setText(displayedIndex + ". ");
        name.setText(aTutorial.getName().fullName);
        moduleCode.setText(aTutorial.getModuleCode().value);
        address.setText(aTutorial.getAddress().value);
        date.setText(aTutorial.getEmail().value);
        aTutorial.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
