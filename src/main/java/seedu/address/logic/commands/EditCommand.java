package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tutorial.Address;
import seedu.address.model.tutorial.Date;
import seedu.address.model.tutorial.TutorialId;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.ModuleCode;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing tutorial in the TAbs.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tutorial identified "
            + "by the index number used in the displayed tutorial list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Tutorial tutorialToEdit = lastShownList.get(index.getZeroBased());
        Tutorial editedTutorial = createEditedTutorial(tutorialToEdit, editTutorialDescriptor);

        if (!tutorialToEdit.isSameTutorial(editedTutorial) && model.hasTutorial(editedTutorial)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setTutorial(tutorialToEdit, editedTutorial);
        model.updateFilteredTutorialList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedTutorial)));
    }

    /**
     * Creates and returns a {@code Tutorial} with the details of {@code tutorialToEdit}
     * edited with {@code editTutorialDescriptor}.
     */
    private static Tutorial createEditedTutorial(Tutorial tutorialToEdit, EditTutorialDescriptor editTutorialDescriptor) {
        assert tutorialToEdit != null;

        TutorialId updatedTutorialId = editTutorialDescriptor.getName().orElse(tutorialToEdit.getName());
        ModuleCode updatedModuleCode = editTutorialDescriptor.getModuleCode().orElse(tutorialToEdit.getModuleCode());
        Date updatedDate = editTutorialDescriptor.getEmail().orElse(tutorialToEdit.getEmail());
        Address updatedAddress = editTutorialDescriptor.getAddress().orElse(tutorialToEdit.getAddress());
        Set<Tag> updatedTags = editTutorialDescriptor.getTags().orElse(tutorialToEdit.getTags());

        return new Tutorial(updatedTutorialId, updatedModuleCode, updatedDate, updatedAddress, updatedTags);
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
        private Address address;
        private Set<Tag> tags;

        public EditTutorialDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTutorialDescriptor(EditTutorialDescriptor toCopy) {
            setName(toCopy.tutorialId);
            setModuleCode(toCopy.moduleCode);
            setEmail(toCopy.date);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(tutorialId, moduleCode, date, address, tags);
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

        public void setEmail(Date date) {
            this.date = date;
        }

        public Optional<Date> getEmail() {
            return Optional.ofNullable(date);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
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
                    && Objects.equals(address, otherEditTutorialDescriptor.address)
                    && Objects.equals(tags, otherEditTutorialDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", tutorialId)
                    .add("moduleCode", moduleCode)
                    .add("date", date)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
