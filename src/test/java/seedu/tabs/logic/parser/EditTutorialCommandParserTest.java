package seedu.tabs.logic.parser;

import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.INVALID_TUTORIAL_DESC;
import static seedu.tabs.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.MODULE_CODE_DESC_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.STUDENT_DESC_A;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.TUTORIAL_DESC_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.FROM;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.EditTutorialCommand;
import seedu.tabs.logic.commands.EditTutorialCommand.EditTutorialDescriptor;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;

public class EditTutorialCommandParserTest {

    private static final String FROM_DESC_C123 = " " + FROM.prefix + VALID_TUTORIAL_C123;

    private static final String MESSAGE_MISSING_FROM =
            String.format(EditTutorialCommand.MESSAGE_FROM_TUTORIAL_ID_MISSING,
                    EditTutorialCommand.MESSAGE_USAGE);

    private final EditTutorialCommandParser parser = new EditTutorialCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no 'from/' tutorial id specified
        assertParseFailure(parser, TUTORIAL_DESC_C123, MESSAGE_MISSING_FROM);

        // no field specified (only from/)
        assertParseFailure(parser, FROM_DESC_C123, EditTutorialCommand.MESSAGE_NOT_EDITED);

        // no from/ and no fields
        assertParseFailure(parser, "", MESSAGE_MISSING_FROM);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid new tutorial id
        assertParseFailure(parser, FROM_DESC_C123 + INVALID_TUTORIAL_DESC, TutorialId.MESSAGE_CONSTRAINTS);

        // invalid module code
        assertParseFailure(parser, FROM_DESC_C123 + INVALID_MODULE_CODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, FROM_DESC_C123 + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // student prefix present (not allowed)
        assertParseFailure(parser, FROM_DESC_C123 + STUDENT_DESC_A,
                EditTutorialCommand.MESSAGE_EDIT_STUDENTS_NOT_ALLOWED);

        // invalid moduleCode followed by valid date -> still moduleCode error
        assertParseFailure(parser, FROM_DESC_C123 + INVALID_MODULE_CODE_DESC + DATE_DESC_C123,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // multiple invalid values, only first captured
        assertParseFailure(parser, FROM_DESC_C123 + INVALID_TUTORIAL_DESC + INVALID_DATE_DESC
                + MODULE_CODE_DESC_CS2103T, TutorialId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // from C123, change to T456, set module + date
        String userInput = FROM_DESC_C123
                + TUTORIAL_DESC_T456
                + MODULE_CODE_DESC_MA1521
                + DATE_DESC_C123;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withId(VALID_TUTORIAL_T456)
                .withModuleCode(VALID_MODULE_CODE_MA1521)
                .withDate(VALID_DATE_C123)
                .build();

        EditTutorialCommand expectedCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // from C123, only change module + date
        String userInput = FROM_DESC_C123 + MODULE_CODE_DESC_MA1521 + DATE_DESC_C123;

        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_MA1521)
                .withDate(VALID_DATE_C123)
                .build();

        EditTutorialCommand expectedCommand = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // change tutorial id only
        String userInput = FROM_DESC_C123 + TUTORIAL_DESC_T456;
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder()
                .withId(VALID_TUTORIAL_T456).build();
        EditTutorialCommand expected = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123), descriptor);
        assertParseSuccess(parser, userInput, expected);

        // change module only
        userInput = FROM_DESC_C123 + MODULE_CODE_DESC_CS2103T;
        descriptor = new EditTutorialDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS2103T).build();
        expected = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123), descriptor);
        assertParseSuccess(parser, userInput, expected);

        // change date only
        userInput = FROM_DESC_C123 + DATE_DESC_C123;
        descriptor = new EditTutorialDescriptorBuilder()
                .withDate(VALID_DATE_C123).build();
        expected = new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(VALID_TUTORIAL_C123), descriptor);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // duplicate fields should be rejected for tid/, mc/, d/
        String userInput;

        // duplicate tutorial id
        userInput = FROM_DESC_C123 + TUTORIAL_DESC_C123 + TUTORIAL_DESC_T456;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(TUTORIAL_ID.prefix));

        // duplicate module code
        userInput = FROM_DESC_C123 + MODULE_CODE_DESC_CS2103T + MODULE_CODE_DESC_MA1521;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(MODULE_CODE.prefix));

        // duplicate date
        userInput = FROM_DESC_C123 + DATE_DESC_C123 + DATE_DESC_C123;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(DATE.prefix));

        // mix of duplicates
        userInput = FROM_DESC_C123
                + MODULE_CODE_DESC_CS2103T + DATE_DESC_C123 + TUTORIAL_DESC_T456
                + MODULE_CODE_DESC_MA1521 + DATE_DESC_C123 + TUTORIAL_DESC_C123;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(TUTORIAL_ID.prefix, MODULE_CODE.prefix, DATE.prefix));
    }

    @Test
    public void parse_studentProvided_failure() {
        // any presence of student prefix should be rejected
        String userInput = FROM_DESC_C123 + STUDENT_DESC_A;
        assertParseFailure(parser, userInput, EditTutorialCommand.MESSAGE_EDIT_STUDENTS_NOT_ALLOWED);
    }
}
