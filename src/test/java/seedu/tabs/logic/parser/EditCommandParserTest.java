package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.tabs.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.EditCommand;
import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_STUDENT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, TutorialId.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, ModuleCode.MESSAGE_CONSTRAINTS); // invalid moduleCode
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Student.MESSAGE_CONSTRAINTS); // invalid student

        // invalid moduleCode followed by valid date
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + DATE_DESC_AMY, ModuleCode.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the students of the {@code Tutorial} being edited,
        // parsing it together with a valid student results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Student.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Student.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Student.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_PHONE_AMY,
                TutorialId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + DATE_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withName(VALID_NAME_AMY)
                .withModuleCode(VALID_PHONE_BOB).withDate(VALID_DATE_AMY)
                .withStudents(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + DATE_DESC_AMY;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withModuleCode(VALID_PHONE_BOB)
                .withDate(VALID_DATE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // moduleCode
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditTutorialDescriptorBuilder().withModuleCode(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
        descriptor = new EditTutorialDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // students
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditTutorialDescriptorBuilder().withStudents(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddTutorialCommandParserTest#parse_repeatedNonStudentValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + DATE_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + DATE_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + DATE_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE, PREFIX_DATE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_DATE_DESC
                + INVALID_PHONE_DESC + INVALID_DATE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE, PREFIX_DATE));
    }

    @Test
    public void parse_resetStudents_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withStudents().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
