package seedu.tabs.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.model.Model.PREDICATE_SHOW_ALL_TUTORIALS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.util.CollectionUtil;
import seedu.tabs.commons.util.ToStringBuilder;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.model.Model;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Edits the details of an existing tutorial in the TAbs.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tutorial identified "
            + "by the index number used in the displayed tutorial list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TUTORIAL_ID + "NAME] "
            + "[" + PREFIX_MODULE_CODE + "PHONE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_STUDENT + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE_CODE + "91234567 "
            + PREFIX_DATE + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Tutorial: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This tutorial already exists in the TAbs.";

    private final Index index;
    private final EditTutorialDescriptor editTutorialDescriptor;

    /**
     * @param index of the tutorial in the filtered tutorial list to edit
     * @param editTutorialDescriptor details to edit the tutorial with
     */
    public EditCommand(Index index, EditTutorialDescriptor editTutorialDescriptor) {
        requireNonNull(index);
        requireNonNull(editTutorialDescriptor);

        this.index = index;
        this.editTutorialDescriptor = new EditTutorialDescriptor(editTutorialDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_ID);
        }

        Tutorial tutorialToEdit = lastShownList.get(index.getZeroBased());
        Tutorial editedTutorial = createEditedTutorial(tutorialToEdit, editTutorialDescriptor);

        if (!tutorialToEdit.isSameTutorial(editedTutorial) && model.hasTutorial(editedTutorial)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setTutorial(tutorialToEdit, editedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_TUTORIALS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedTutorial)));
    }

    /**
     * Creates and returns a {@code Tutorial} with the details of {@code tutorialToEdit}
     * edited with {@code editTutorialDescriptor}.
     */
    private static Tutorial createEditedTutorial(Tutorial tutorialToEdit,
                                                 EditTutorialDescriptor editTutorialDescriptor) {
        assert tutorialToEdit != null;

        TutorialId updatedTutorialId = editTutorialDescriptor.getName().orElse(tutorialToEdit.getTutorialId());
        ModuleCode updatedModuleCode = editTutorialDescriptor.getModuleCode().orElse(tutorialToEdit.getModuleCode());
        Date updatedDate = editTutorialDescriptor.getDate().orElse(tutorialToEdit.getDate());
        Set<Student> updatedStudents = editTutorialDescriptor.getStudents().orElse(tutorialToEdit.getStudents());

        return new Tutorial(updatedTutorialId, updatedModuleCode, updatedDate, updatedStudents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editTutorialDescriptor.equals(otherEditCommand.editTutorialDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editTutorialDescriptor", editTutorialDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the tutorial with. Each non-empty field value will replace the
     * corresponding field value of the tutorial.
     */
    public static class EditTutorialDescriptor {
        private TutorialId tutorialId;
        private ModuleCode moduleCode;
        private Date date;
        private Set<Student> students;

        public EditTutorialDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTutorialDescriptor(EditTutorialDescriptor toCopy) {
            setName(toCopy.tutorialId);
            setModuleCode(toCopy.moduleCode);
            setDate(toCopy.date);
            setStudents(toCopy.students);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(tutorialId, moduleCode, date, students);
        }

        public void setName(TutorialId tutorialId) {
            this.tutorialId = tutorialId;
        }

        public Optional<TutorialId> getName() {
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setStudents(Set<Student> students) {
            this.students = (students != null) ? new HashSet<>(students) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Student>> getStudents() {
            return (students != null) ? Optional.of(Collections.unmodifiableSet(students)) : Optional.empty();
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
                    && Objects.equals(date, otherEditTutorialDescriptor.date)
                    && Objects.equals(students, otherEditTutorialDescriptor.students);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("tutorialId", tutorialId)
                    .add("moduleCode", moduleCode)
                    .add("date", date)
                    .add("students", students)
                    .toString();
        }
    }
}
