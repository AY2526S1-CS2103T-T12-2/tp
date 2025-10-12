package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;

public class EditTutorialDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTutorialDescriptor descriptorWithSameValues = new EditTutorialDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditTutorialDescriptor editedAmy = new EditTutorialDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different moduleCode -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_AMY).withModuleCode(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tabs -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different students -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_AMY).withStudents(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditTutorialDescriptor editTutorialDescriptor = new EditTutorialDescriptor();
        String expected = EditTutorialDescriptor.class.getCanonicalName() + "{tutorialId="
                + editTutorialDescriptor.getName().orElse(null) + ", moduleCode="
                + editTutorialDescriptor.getModuleCode().orElse(null) + ", date="
                + editTutorialDescriptor.getDate().orElse(null) + ", tabs="
                + editTutorialDescriptor.getAddress().orElse(null) + ", students="
                + editTutorialDescriptor.getStudents().orElse(null) + "}";
        assertEquals(expected, editTutorialDescriptor.toString());
    }
}
