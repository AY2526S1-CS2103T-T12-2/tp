package seedu.tabs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.commands.CommandTestUtil.DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;

public class EditTutorialDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTutorialDescriptor descriptorWithSameValues = new EditTutorialDescriptor(DESC_C123);
        assertTrue(DESC_C123.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_C123.equals(DESC_C123));

        // null -> returns false
        assertFalse(DESC_C123.equals(null));

        // different types -> returns false
        assertFalse(DESC_C123.equals(5));

        // different values -> returns false
        assertFalse(DESC_C123.equals(DESC_T456));

        // different name -> returns false
        EditTutorialDescriptor editedAmy = new EditTutorialDescriptorBuilder(DESC_C123)
                .withId(VALID_TUTORIAL_T456).build();
        assertFalse(DESC_C123.equals(editedAmy));

        // different moduleCode -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_C123)
                .withModuleCode(VALID_MODULE_CODE_MA1521).build();
        assertFalse(DESC_C123.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_C123).withDate(VALID_DATE_T456).build();
        assertFalse(DESC_C123.equals(editedAmy));

        // different students -> returns false
        editedAmy = new EditTutorialDescriptorBuilder(DESC_C123).withStudents(VALID_STUDENT_A).build();
        assertFalse(DESC_C123.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditTutorialDescriptor editTutorialDescriptor = new EditTutorialDescriptor();
        String expected = EditTutorialDescriptor.class.getCanonicalName() + "{tutorialId="
                + editTutorialDescriptor.getName().orElse(null) + ", moduleCode="
                + editTutorialDescriptor.getModuleCode().orElse(null) + ", date="
                + editTutorialDescriptor.getDate().orElse(null) + ", students="
                + editTutorialDescriptor.getStudents().orElse(null) + "}";
        assertEquals(expected, editTutorialDescriptor.toString());
    }
}
