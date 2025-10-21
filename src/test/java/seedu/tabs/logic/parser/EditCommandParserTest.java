package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_STUDENT_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_TUTORIAL_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.MODULE_CODE_DESC_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.STUDENT_DESC_A;
import static seedu.tabs.logic.commands.CommandTestUtil.STUDENT_DESC_B;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tabs.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.EditCommand;
import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;

public class EditCommandParserTest {

    private static final String STUDENT_EMPTY = " " + PREFIX_STUDENT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TUTORIAL_C123, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TUTORIAL_DESC_C123, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TUTORIAL_DESC_C123, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TUTORIAL_DESC, TutorialId.MESSAGE_CONSTRAINTS); // invalid name
        // invalid moduleCode
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_STUDENT_DESC, Student.MESSAGE_CONSTRAINTS); // invalid student

        // invalid moduleCode followed by valid date
        assertParseFailure(parser, "1" + INVALID_MODULE_CODE_DESC + DATE_DESC_C123,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_STUDENT} alone will reset the students of the {@code Tutorial} being edited,
        // parsing it together with a valid student results in error
        assertParseFailure(parser, "1" + STUDENT_DESC_B + STUDENT_DESC_A + STUDENT_EMPTY + PREFIX_STUDENT,
                Student.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + STUDENT_DESC_B + STUDENT_EMPTY + PREFIX_STUDENT + STUDENT_DESC_A,
                Student.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + STUDENT_EMPTY + PREFIX_STUDENT + STUDENT_DESC_B + STUDENT_DESC_A,
                Student.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TUTORIAL_DESC + INVALID_DATE_DESC + VALID_MODULE_CODE_CS2103T,
                TutorialId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_MA1521 + STUDENT_DESC_A
                + DATE_DESC_C123 + TUTORIAL_DESC_C123 + STUDENT_DESC_B;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withId(VALID_TUTORIAL_C123)
                .withModuleCode(VALID_MODULE_CODE_MA1521).withDate(VALID_DATE_C123)
                .withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_MA1521 + DATE_DESC_C123;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_MA1521).withDate(VALID_DATE_C123).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TUTORIAL_DESC_C123;
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withId(VALID_TUTORIAL_C123).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // moduleCode
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2103T;
        descriptor = new EditTutorialDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_CS2103T).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_C123;
        descriptor = new EditTutorialDescriptorBuilder().withDate(VALID_DATE_C123).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // students
        userInput = targetIndex.getOneBased() + STUDENT_DESC_B;
        descriptor = new EditTutorialDescriptorBuilder().withStudents(VALID_STUDENT_B).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddTutorialCommandParserTest#parse_repeatedNonStudentValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_MODULE_CODE_DESC + MODULE_CODE_DESC_MA1521;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_MA1521 + INVALID_MODULE_CODE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2103T + DATE_DESC_C123
                + STUDENT_DESC_B + MODULE_CODE_DESC_CS2103T + DATE_DESC_C123 + STUDENT_DESC_B
                + MODULE_CODE_DESC_MA1521 + DATE_DESC_C123 + STUDENT_DESC_A;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE, PREFIX_DATE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_MODULE_CODE_DESC + INVALID_DATE_DESC
                + INVALID_MODULE_CODE_DESC + INVALID_DATE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE, PREFIX_DATE));
    }

    @Test
    public void parse_resetStudents_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + STUDENT_EMPTY;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder().withStudents().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
