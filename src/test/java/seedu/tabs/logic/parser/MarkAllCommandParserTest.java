package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.CommandTestUtil;
import seedu.tabs.logic.commands.MarkAllCommand;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TypicalPredicates;

public class MarkAllCommandParserTest {

    private final MarkAllCommandParser parser = new MarkAllCommandParser();

    @Test
    public void parse_validArgs_returnsMarkAllCommand() {
        assertParseSuccess(parser, CommandTestUtil.TUTORIAL_DESC_C123,
                new MarkAllCommand(TypicalPredicates.PREDICATE_KEYWORD_C123));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAllCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTutorialId_throwsParseException() {
        assertParseFailure(parser, CommandTestUtil.INVALID_TUTORIAL_DESC, TutorialId.MESSAGE_CONSTRAINTS);
    }
}
