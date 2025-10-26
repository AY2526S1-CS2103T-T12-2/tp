package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.CommandTestUtil;
import seedu.tabs.logic.commands.DeleteTutorialCommand;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TypicalPredicates;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * beyond the DeleteTutorialCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteTutorialCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTutorialCommandParserTest {

    private final DeleteTutorialCommandParser parser = new DeleteTutorialCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, CommandTestUtil.TUTORIAL_DESC_C123,
                new DeleteTutorialCommand(TypicalPredicates.PREDICATE_KEYWORD_C123));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTutorialId_throwsParseException() {
        assertParseFailure(parser, CommandTestUtil.INVALID_TUTORIAL_DESC, TutorialId.MESSAGE_CONSTRAINTS);
    }
}
