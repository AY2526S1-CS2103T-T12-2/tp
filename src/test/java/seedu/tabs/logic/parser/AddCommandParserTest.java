package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tabs.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tabs.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.tabs.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tabs.testutil.TypicalTutorials.AMY;
import static seedu.tabs.testutil.TypicalTutorials.BOB;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.AddCommand;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Address;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TutorialBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tutorial expectedTutorial = new TutorialBuilder(BOB).withStudents(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedTutorial));


        // multiple students - all accepted
        Tutorial expectedTutorialMultipleStudents = new TutorialBuilder(BOB).withStudents(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedTutorialMultipleStudents));
    }

    @Test
    public void parse_repeatedNonStudentValue_failure() {
        String validExpectedTutorialString = NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));

        // multiple moduleCodes
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_AMY + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedTutorialString + PHONE_DESC_AMY + DATE_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID, PREFIX_ADDRESS, PREFIX_DATE, PREFIX_MODULE_CODE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid moduleCode
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // invalid tabs
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedTutorialString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));

        // invalid date
        assertParseFailure(parser, validExpectedTutorialString + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid moduleCode
        assertParseFailure(parser, validExpectedTutorialString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // invalid tabs
        assertParseFailure(parser, validExpectedTutorialString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero students
        Tutorial expectedTutorial = new TutorialBuilder(AMY).withStudents().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + DATE_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedTutorial));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing moduleCode prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_DATE_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing tabs prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_DATE_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, TutorialId.MESSAGE_CONSTRAINTS);

        // invalid moduleCode
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_DATE_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid tabs
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid student
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Student.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + DATE_DESC_BOB + INVALID_ADDRESS_DESC,
                TutorialId.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
