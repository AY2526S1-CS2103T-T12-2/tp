package seedu.tabs.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final String ERROR_STYLE_CLASS = "error";
    private static final String SUCCESS_STYLE_CLASS = "success";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        setStyleToSuccess();
    }

    /**
     * Sets the result display to show an error message.
     */
    public void setErrorMessage(String errorMessage) {
        requireNonNull(errorMessage);
        resultDisplay.setText(errorMessage);
        setStyleToError();
    }

    /**
     * Sets the result display style to indicate an error.
     */
    private void setStyleToError() {
        resultDisplay.getStyleClass().removeAll(SUCCESS_STYLE_CLASS, ERROR_STYLE_CLASS);
        resultDisplay.getStyleClass().add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the result display style to indicate success.
     */
    private void setStyleToSuccess() {
        resultDisplay.getStyleClass().removeAll(SUCCESS_STYLE_CLASS, ERROR_STYLE_CLASS);
        resultDisplay.getStyleClass().add(SUCCESS_STYLE_CLASS);
    }

}
