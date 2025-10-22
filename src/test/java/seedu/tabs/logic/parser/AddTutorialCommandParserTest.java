package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_STUDENT_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_TUTORIAL_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.MODULE_CODE_DESC_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tabs.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tabs.logic.commands.CommandTestUtil.STUDENT_DESC_A;
import static seedu.tabs.logic.commands.CommandTestUtil.STUDENT_DESC_B;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_TEST_C123;
import static seedu.tabs.testutil.TypicalTutorials.TUTORIAL_TEST_T456;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.AddTutorialCommand;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TutorialBuilder;

public class AddTutorialCommandParserTest {
    private AddTutorialCommandParser parser = new AddTutorialCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tutorial expectedTutorial = new TutorialBuilder(TUTORIAL_TEST_T456).withStudents(VALID_STUDENT_B).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456
                + STUDENT_DESC_B, new AddTutorialCommand(expectedTutorial));


        // multiple students - all accepted
        Tutorial expectedTutorialMultipleStudents =
                new TutorialBuilder(TUTORIAL_TEST_T456).withStudents(VALID_STUDENT_B, VALID_STUDENT_A).build();
        assertParseSuccess(parser,
                TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456
                        + STUDENT_DESC_A + STUDENT_DESC_B,
                new AddTutorialCommand(expectedTutorialMultipleStudents));
    }

    @Test
    public void parse_repeatedNonStudentValue_failure() {
        String validExpectedTutorialString = TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456
                + STUDENT_DESC_B;

        // multiple ids
        assertParseFailure(parser, TUTORIAL_DESC_C123 + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));

        // multiple moduleCodes
        assertParseFailure(parser, MODULE_CODE_DESC_CS2103T + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_C123 + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedTutorialString + MODULE_CODE_DESC_CS2103T + DATE_DESC_C123 + TUTORIAL_DESC_C123
                        + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID,
                        PREFIX_DATE, PREFIX_MODULE_CODE));

        // invalid value followed by valid value

        // invalid id
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid moduleCode
        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + validExpectedTutorialString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // valid value followed by invalid value

        // invalid id
        assertParseFailure(parser, validExpectedTutorialString + INVALID_TUTORIAL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));

        // invalid date
        assertParseFailure(parser, validExpectedTutorialString + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid moduleCode
        assertParseFailure(parser, validExpectedTutorialString + INVALID_MODULE_CODE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero students
        Tutorial expectedTutorial = new TutorialBuilder(TUTORIAL_TEST_C123).withStudents().build();
        assertParseSuccess(parser, TUTORIAL_DESC_C123 + MODULE_CODE_DESC_CS2103T + DATE_DESC_C123,
                new AddTutorialCommand(expectedTutorial));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE);

        // missing id prefix
        assertParseFailure(parser, VALID_TUTORIAL_T456 + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456,
                expectedMessage);

        // missing moduleCode prefix
        assertParseFailure(parser, TUTORIAL_DESC_T456 + VALID_MODULE_CODE_MA1521 + DATE_DESC_T456,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + VALID_DATE_T456,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TUTORIAL_T456 + VALID_MODULE_CODE_MA1521 + VALID_DATE_T456,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid id
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456
                + STUDENT_DESC_A + STUDENT_DESC_B, TutorialId.MESSAGE_CONSTRAINTS);

        // invalid moduleCode
        assertParseFailure(parser, TUTORIAL_DESC_T456 + INVALID_MODULE_CODE_DESC + DATE_DESC_T456
                + STUDENT_DESC_A + STUDENT_DESC_B, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + INVALID_DATE_DESC
                + STUDENT_DESC_A + STUDENT_DESC_B, Date.MESSAGE_CONSTRAINTS);

        // invalid student
        assertParseFailure(parser, TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456
                + INVALID_STUDENT_DESC + VALID_STUDENT_B, Student.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + MODULE_CODE_DESC_MA1521 + INVALID_DATE_DESC,
                TutorialId.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TUTORIAL_DESC_T456 + MODULE_CODE_DESC_MA1521 + DATE_DESC_T456
                        + STUDENT_DESC_A + STUDENT_DESC_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnlyInput_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE));
    }
}
