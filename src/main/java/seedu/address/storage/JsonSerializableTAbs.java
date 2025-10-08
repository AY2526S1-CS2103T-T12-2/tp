package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TAbs;
import seedu.address.model.ReadOnlyTAbs;
import seedu.address.model.tutorial.Tutorial;

/**
 * An Immutable TAbs that is serializable to JSON format.
 */
@JsonRootName(value = "tabs")
class JsonSerializableTAbs {

    public static final String MESSAGE_DUPLICATE_PERSON = "Tutorials list contains duplicate tutorial(s).";

    private final List<JsonAdaptedTutorial> tutorials = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTAbs} with the given tutorials.
     */
    @JsonCreator
    public JsonSerializableTAbs(@JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials) {
        this.tutorials.addAll(tutorials);
    }

    /**
     * Converts a given {@code ReadOnlyTAbs} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTAbs}.
     */
    public JsonSerializableTAbs(ReadOnlyTAbs source) {
        tutorials.addAll(source.getTutorialList().stream().map(JsonAdaptedTutorial::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TAbs into the model's {@code TAbs} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TAbs toModelType() throws IllegalValueException {
        TAbs TAbs = new TAbs();
        for (JsonAdaptedTutorial jsonAdaptedTutorial : tutorials) {
            Tutorial aTutorial = jsonAdaptedTutorial.toModelType();
            if (TAbs.hasTutorial(aTutorial)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            TAbs.addTutorial(aTutorial);
        }
        return TAbs;
    }

}
