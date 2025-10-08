package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.DeleteTutorialCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTutorialCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    //    @Test
    //    public void parse_validArgs_returnsDeleteCommand() {
    //        assertParseSuccess(parser, "1", new DeleteTutorialCommand(INDEX_FIRST_PERSON));
    //    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTutorialCommand.MESSAGE_USAGE));
    }
}
