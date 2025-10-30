package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.CommandTestUtil;
import seedu.tabs.logic.commands.MarkCommand;
import seedu.tabs.model.student.Student;
import seedu.tabs.testutil.TypicalPredicates;

public class MarkCommandParserTest {
    private final MarkCommandParser parser = new MarkCommandParser();
    private final Set<Student> studentSet = new HashSet<>(List.of(
            new Student(CommandTestUtil.VALID_STUDENT_A)
    ));

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        //Tutorial id and student id given
        String userInput = CommandTestUtil.TUTORIAL_DESC_C123 + CommandTestUtil.STUDENT_DESC_A;
        assertParseSuccess(parser, userInput,
                new MarkCommand(studentSet, TypicalPredicates.PREDICATE_KEYWORD_C123));
    }

    @Test
    public void parse_missingTutorialIdPrefix_failure() {
        //Tutorial id (t/) prefix not given
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        String userInput = CommandTestUtil.VALID_TUTORIAL_C123 + CommandTestUtil.STUDENT_DESC_A;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingTutorialIdValue_failure() {
        // missing tutorial Id after prefix
        String userInput = CliSyntax.PREFIX_TUTORIAL_ID + CommandTestUtil.STUDENT_DESC_A;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingStudentIdPrefix_failure() {
        //Student id (id/) prefix not given
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        String userInput = CommandTestUtil.TUTORIAL_DESC_C123 + CommandTestUtil.VALID_STUDENT_A;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingStudentIdValue_failure() {
        // missing student id after prefix
        String userInput = CommandTestUtil.TUTORIAL_DESC_C123 + CliSyntax.PREFIX_STUDENT;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
