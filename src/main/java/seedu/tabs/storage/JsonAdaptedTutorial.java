package seedu.tabs.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
class JsonAdaptedTutorial {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";

    private final String tutorialId;
    private final String moduleCode;
    private final String date;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given tutorial details.
     */
    @JsonCreator
    public JsonAdaptedTutorial(@JsonProperty("id") String id, @JsonProperty("moduleCode") String moduleCode,
            @JsonProperty("date") String date,
            @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.tutorialId = id;
        this.moduleCode = moduleCode;
        this.date = date;
        if (students != null) {
            this.students.addAll(students);
        }
    }

    /**
     * Converts a given {@code Tutorial} into this class for Jackson use.
     */
    public JsonAdaptedTutorial(Tutorial source) {
        tutorialId = source.getTutorialId().id;
        moduleCode = source.getModuleCode().value;
        date = source.getDate().value;
        students.addAll(source.getStudents().stream()
                .map(JsonAdaptedStudent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's {@code Tutorial} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutorial.
     */
    public Tutorial toModelType() throws IllegalValueException {
        final List<Student> tutorialStudents = new ArrayList<>();
        for (JsonAdaptedStudent student : students) {
            tutorialStudents.add(student.toModelType());
        }

        if (tutorialId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TutorialId.class.getSimpleName()));
        }
        if (!TutorialId.isValidTutorialId(tutorialId)) {
            throw new IllegalValueException(TutorialId.MESSAGE_CONSTRAINTS);
        }
        final TutorialId modelTutorialId = new TutorialId(tutorialId);

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        final Set<Student> modelStudents = new HashSet<>(tutorialStudents);
        return new Tutorial(modelTutorialId, modelModuleCode, modelDate, modelStudents);
    }

}
