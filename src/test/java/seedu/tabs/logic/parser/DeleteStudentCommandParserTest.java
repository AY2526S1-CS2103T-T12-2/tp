package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.CommandTestUtil;
import seedu.tabs.logic.commands.DeleteStudentCommand;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.TypicalPredicates;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * beyond the DeleteStudentCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteStudentCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteStudentCommandParserTest {

    private final DeleteStudentCommandParser parser = new DeleteStudentCommandParser();
    private final Student studentA = new Student(VALID_STUDENT_A);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        String userInput = CommandTestUtil.TUTORIAL_DESC_C123 + CommandTestUtil.STUDENT_DESC_A;
        assertParseSuccess(parser, userInput,
                new DeleteStudentCommand(TypicalPredicates.PREDICATE_KEYWORD_C123, studentA));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTutorialId_throwsParseException() {
        String userInput = CommandTestUtil.INVALID_TUTORIAL_DESC + CommandTestUtil.STUDENT_DESC_A;
        assertParseFailure(parser, userInput, TutorialId.MESSAGE_CONSTRAINTS);
    }
}
