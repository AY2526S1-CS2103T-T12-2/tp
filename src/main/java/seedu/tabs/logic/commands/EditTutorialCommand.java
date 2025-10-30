package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.FROM;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.tabs.commons.util.CollectionUtil;
import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Edits the details of an existing tutorial in the TAbs.
 */
public class EditTutorialCommand extends Command {

    public static final String COMMAND_WORD = "edit_tutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tutorial identified "
            + "by the given tutorial ID.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + FROM.prefix + "ORIGINAL_TUTORIAL ID "
            + "[" + TUTORIAL_ID.prefix + "NEW_TUTORIAL_ID] "
            + "[" + MODULE_CODE.prefix + "NEW_MODULE_CODE] "
            + "[" + DATE.prefix + "NEW_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + FROM.prefix + "T123 "
            + TUTORIAL_ID.prefix + "T456 "
            + MODULE_CODE.prefix + "CS2103T "
            + DATE.prefix + "2025-10-25";

    public static final String MESSAGE_EDIT_TUTORIAL_SUCCESS = "Edited Tutorial: \n%1$s";
    public static final String MESSAGE_FROM_TUTORIAL_ID_MISSING = "Tutorial ID of the tutorial to be edited "
            + "must be specified.\n%1$s.";
    public static final String MESSAGE_EDIT_STUDENTS_NOT_ALLOWED = "Students cannot be edited via this command.\n"
            + "Please use the add_student or delete_student commands instead.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in TAbs.";


    private final TutorialIdMatchesKeywordPredicate predicate;
    private final EditTutorialDescriptor editTutorialDescriptor;

    /**
     * @param predicate to filter the tutorial list by the provided tutorial_id
     * @param editTutorialDescriptor details to edit the tutorial with
     */
    public EditTutorialCommand(TutorialIdMatchesKeywordPredicate predicate,
                               EditTutorialDescriptor editTutorialDescriptor) {
        requireNonNull(predicate);
        requireNonNull(editTutorialDescriptor);

        this.predicate = predicate;
        this.editTutorialDescriptor = new EditTutorialDescriptor(editTutorialDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getTAbs().getTutorialList();
        Tutorial tutorialToEdit = lastShownList.stream().filter(predicate).findFirst().orElse(null);

        if (tutorialToEdit == null) {
            throw new CommandException(Messages.MESSAGE_TUTORIAL_ID_NOT_FOUND);
        }

        Tutorial editedTutorial = createEditedTutorial(tutorialToEdit, editTutorialDescriptor);

        if (!tutorialToEdit.isSameTutorial(editedTutorial) && model.hasTutorial(editedTutorial)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        model.setTutorial(tutorialToEdit, editedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
        return new CommandResult(String.format(MESSAGE_EDIT_TUTORIAL_SUCCESS, Messages.format(editedTutorial)));
    }

    /**
     * Creates and returns a {@code Tutorial} with the details of {@code tutorialToEdit}
     * edited with {@code editTutorialDescriptor}.
     */
    private static Tutorial createEditedTutorial(Tutorial tutorialToEdit,
                                                 EditTutorialDescriptor editTutorialDescriptor) {
        assert tutorialToEdit != null;

        TutorialId updatedTutorialId = editTutorialDescriptor.getId().orElse(tutorialToEdit.getTutorialId());
        ModuleCode updatedModuleCode = editTutorialDescriptor.getModuleCode().orElse(tutorialToEdit.getModuleCode());
        Date updatedDate = editTutorialDescriptor.getDate().orElse(tutorialToEdit.getDate());

        return new Tutorial(updatedTutorialId, updatedModuleCode, updatedDate, tutorialToEdit.getStudents());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTutorialCommand)) {
            return false;
        }

        EditTutorialCommand otherEditTutorialCommand = (EditTutorialCommand) other;
        return predicate.equals(otherEditTutorialCommand.predicate)
                && editTutorialDescriptor.equals(otherEditTutorialCommand.editTutorialDescriptor);
    }

    /**
     * Stores the details to edit the tutorial with. Each non-empty field value will replace the
     * corresponding field value of the tutorial.
     */
    public static class EditTutorialDescriptor {
        private TutorialId tutorialId;
        private ModuleCode moduleCode;
        private Date date;

        public EditTutorialDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code students} is used internally.
         */
        public EditTutorialDescriptor(EditTutorialDescriptor toCopy) {
            setId(toCopy.tutorialId);
            setModuleCode(toCopy.moduleCode);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(tutorialId, moduleCode, date);
        }

        public void setId(TutorialId tutorialId) {
            this.tutorialId = tutorialId;
        }

        public Optional<TutorialId> getId() {
            return Optional.ofNullable(tutorialId);
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTutorialDescriptor)) {
                return false;
            }

            EditTutorialDescriptor otherEditTutorialDescriptor = (EditTutorialDescriptor) other;
            return Objects.equals(tutorialId, otherEditTutorialDescriptor.tutorialId)
                    && Objects.equals(moduleCode, otherEditTutorialDescriptor.moduleCode)
                    && Objects.equals(date, otherEditTutorialDescriptor.date);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("tutorialId", tutorialId)
                    .add("moduleCode", moduleCode)
                    .add("date", date)
                    .toString();
        }
    }
}
