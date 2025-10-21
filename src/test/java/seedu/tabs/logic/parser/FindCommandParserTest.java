package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
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
        String moduleCodeKeyword = VALID_MODULE_CODE_CS2103T;
        String tutorialIdKeyword = VALID_TUTORIAL_C123;

        FindCommand expectedFindCommand = new FindCommand(
                new TutorialIdContainsKeywordsPredicate(Arrays.asList(moduleCodeKeyword, tutorialIdKeyword)));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, moduleCodeKeyword + " " + tutorialIdKeyword, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + moduleCodeKeyword + " \n \t " + tutorialIdKeyword + "  \t",
                expectedFindCommand);

        // single keyword (just the tutorial ID)
        FindCommand expectedFindCommandSingle = new FindCommand(
                new TutorialIdContainsKeywordsPredicate(Arrays.asList(tutorialIdKeyword)));
        assertParseSuccess(parser, tutorialIdKeyword, expectedFindCommandSingle);
    }


}
