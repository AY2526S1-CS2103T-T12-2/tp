package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_C2;
import static seedu.tabs.logic.commands.CommandTestUtil.FROM_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.FROM_DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_FROM_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_TUTORIAL_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tabs.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_C2;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_C2;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C2;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.FROM;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.CopyTutorialCommand;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.TutorialId;

public class CopyTutorialCommandParserTest {
    private CopyTutorialCommandParser parser = new CopyTutorialCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        TutorialId newTutorialId = new TutorialId(VALID_TUTORIAL_C2);
        TutorialId sourceTutorialId = new TutorialId(VALID_TUTORIAL_C123);
        Date newDate = new Date(VALID_DATE_C2);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TUTORIAL_DESC_C2 + FROM_DESC_C123 + DATE_DESC_C2,
                new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate));

        // different order of parameters
        assertParseSuccess(parser, DATE_DESC_C2 + TUTORIAL_DESC_C2 + FROM_DESC_C123,
                new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate));

        assertParseSuccess(parser, FROM_DESC_C123 + DATE_DESC_C2 + TUTORIAL_DESC_C2,
                new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyTutorialCommand.MESSAGE_USAGE);

        // missing new tutorial ID prefix
        assertParseFailure(parser, VALID_TUTORIAL_C2 + FROM_DESC_C123 + DATE_DESC_C2,
                expectedMessage);

        // missing source tutorial ID prefix
        assertParseFailure(parser, TUTORIAL_DESC_C2 + VALID_TUTORIAL_C123 + DATE_DESC_C2,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, TUTORIAL_DESC_C2 + FROM_DESC_C123 + VALID_DATE_C2,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TUTORIAL_C2 + VALID_TUTORIAL_C123 + VALID_DATE_C2,
                expectedMessage);

        // missing new tutorial ID
        assertParseFailure(parser, FROM_DESC_C123 + DATE_DESC_C2,
                expectedMessage);

        // missing source tutorial ID
        assertParseFailure(parser, TUTORIAL_DESC_C2 + DATE_DESC_C2,
                expectedMessage);

        // missing date
        assertParseFailure(parser, TUTORIAL_DESC_C2 + FROM_DESC_C123,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid new tutorial ID
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + FROM_DESC_C123 + DATE_DESC_C2,
                TutorialId.MESSAGE_CONSTRAINTS);

        // invalid source tutorial ID
        assertParseFailure(parser, TUTORIAL_DESC_C2 + INVALID_FROM_DESC + DATE_DESC_C2,
                TutorialId.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, TUTORIAL_DESC_C2 + FROM_DESC_C123 + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + FROM_DESC_C123 + INVALID_DATE_DESC,
                TutorialId.MESSAGE_CONSTRAINTS);

        // all invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + INVALID_FROM_DESC + INVALID_DATE_DESC,
                TutorialId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validCommand = TUTORIAL_DESC_C2 + FROM_DESC_C123 + DATE_DESC_C2;

        // multiple new tutorial IDs
        assertParseFailure(parser, TUTORIAL_DESC_T456 + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(TUTORIAL_ID.prefix));

        // multiple source tutorial IDs
        assertParseFailure(parser, FROM_DESC_T456 + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(FROM.prefix));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_C123 + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(DATE.prefix));

        // multiple fields repeated
        assertParseFailure(parser,
                validCommand + TUTORIAL_DESC_T456 + FROM_DESC_T456 + DATE_DESC_C123,
                Messages.getErrorMessageForDuplicatePrefixes(TUTORIAL_ID.prefix, FROM.prefix, DATE.prefix));

        // invalid value followed by valid value

        // invalid new tutorial ID
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(TUTORIAL_ID.prefix));

        // invalid source tutorial ID
        assertParseFailure(parser, INVALID_FROM_DESC + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(FROM.prefix));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validCommand,
                Messages.getErrorMessageForDuplicatePrefixes(DATE.prefix));

        // valid value followed by invalid value

        // invalid new tutorial ID
        assertParseFailure(parser, validCommand + INVALID_TUTORIAL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(TUTORIAL_ID.prefix));

        // invalid source tutorial ID
        assertParseFailure(parser, validCommand + INVALID_FROM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(FROM.prefix));

        // invalid date
        assertParseFailure(parser, validCommand + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(DATE.prefix));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TUTORIAL_DESC_C2 + FROM_DESC_C123 + DATE_DESC_C2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnlyInput_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyTutorialCommand.MESSAGE_USAGE));
    }
}
