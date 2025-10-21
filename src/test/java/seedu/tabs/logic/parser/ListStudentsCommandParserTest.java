package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.ListStudentsCommand;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TutorialBuilder;

public class ListStudentsCommandParserTest {

    private static final String VALID_TUTORIAL_ID_STRING = TutorialBuilder.DEFAULT_NAME;
    private static final TutorialId VALID_TUTORIAL_ID = new TutorialBuilder().build().getTutorialId();

    private ListStudentsCommandParser parser = new ListStudentsCommandParser();

    @Test
    public void parse_validArgs_returnsListStudentsCommand() {
        // Only the tutorial ID prefix and value
        assertParseSuccess(parser, " " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_ID_STRING,
                new ListStudentsCommand(VALID_TUTORIAL_ID));

        // Leading and trailing whitespace should be ignored
        assertParseSuccess(parser, "  " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_ID_STRING + " ",
                new ListStudentsCommand(VALID_TUTORIAL_ID));
    }

    @Test
    public void parse_missingTutorialIdPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStudentsCommand.MESSAGE_USAGE);

        // Missing tutorial ID prefix completely
        assertParseFailure(parser, VALID_TUTORIAL_ID_STRING, expectedMessage);
    }

    @Test
    public void parse_missingTutorialIdValue_failure() {
        String expectedMessage = TutorialId.MESSAGE_CONSTRAINTS;

        // missing value after prefix
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_ID, expectedMessage);
    }

    @Test
    public void parse_invalidTutorialIdValue_failure() {
        // Assuming TutorialId has validation constraints
        String expectedMessage = TutorialId.MESSAGE_CONSTRAINTS;

        // Invalid tutorial ID (e.g., empty string which should be caught by parseTutorialId)
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_ID + " ", expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStudentsCommand.MESSAGE_USAGE);

        // Pre-amble not allowed (unexpected text before the required prefixes)
        assertParseFailure(parser, "extra words " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_ID_STRING,
                expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_ID_STRING, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStudentsCommand.MESSAGE_USAGE);

        // Duplicate tutorial ID prefixes (e.g. list_students t/C999 t/C100)
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_ID_STRING
                        + " " + PREFIX_TUTORIAL_ID + VALID_TUTORIAL_ID_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID));
    }
}
