package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.FindCommand;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Keywords containing a tutorial ID and a module code (case-insensitive)
        FindCommand expectedFindCommand =
                new FindCommand(new TutorialIdContainsKeywordsPredicate(Arrays.asList("cs2103t", "T09")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "cs2103t T09", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n cs2103t \n \t T09  \t", expectedFindCommand);
    }

}
