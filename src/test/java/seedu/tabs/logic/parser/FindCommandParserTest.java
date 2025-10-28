package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C2;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tabs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.Messages;
import seedu.tabs.logic.commands.FindCommand;
import seedu.tabs.model.tutorial.ModuleCodeAndTutorialIdContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.ModuleCodeContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validArgsModuleCode_returnsFindCommand() {
        String userInput = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2103T + " " + VALID_MODULE_CODE_MA1521;
        List<String> keywords = Arrays.asList(VALID_MODULE_CODE_CS2103T, VALID_MODULE_CODE_MA1521);
        ModuleCodeContainsKeywordsPredicate expectedPredicate =
                new ModuleCodeContainsKeywordsPredicate(keywords);
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, userInput, expectedCommand);

        // Multiple keywords with extra spacing (VALID_MODULE_CODE_MA1521 and VALID_MODULE_CODE_CS2103T)
        userInput = " " + PREFIX_MODULE_CODE + " \t" + VALID_MODULE_CODE_MA1521 + "\t\t "
                + VALID_MODULE_CODE_CS2103T + " \t";
        keywords = Arrays.asList(VALID_MODULE_CODE_MA1521, VALID_MODULE_CODE_CS2103T);
        expectedPredicate = new ModuleCodeContainsKeywordsPredicate(keywords);
        expectedCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Single keyword
        userInput = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_MA1521;
        keywords = Collections.singletonList(VALID_MODULE_CODE_MA1521);
        expectedPredicate = new ModuleCodeContainsKeywordsPredicate(keywords);
        expectedCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validArgsTutorialId_returnsFindCommand() {
        // Find tutorials with tutorial IDs containing VALID_TUTORIAL_C123 and VALID_TUTORIAL_T456
        String userInput = " " + PREFIX_TUTORIAL_ID + " " + VALID_TUTORIAL_C123 + " " + VALID_TUTORIAL_T456;
        List<String> keywords = Arrays.asList(VALID_TUTORIAL_C123, VALID_TUTORIAL_T456);
        TutorialIdContainsKeywordsPredicate expectedPredicate =
                new TutorialIdContainsKeywordsPredicate(keywords);
        FindCommand expectedCommand = new FindCommand(expectedPredicate);

        assertParseSuccess(parser, userInput, expectedCommand);

        // Single keyword (VALID_TUTORIAL_C2)
        userInput = " " + PREFIX_TUTORIAL_ID + " " + VALID_TUTORIAL_C2;
        keywords = Collections.singletonList(VALID_TUTORIAL_C2);
        expectedPredicate = new TutorialIdContainsKeywordsPredicate(keywords);
        expectedCommand = new FindCommand(expectedPredicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

        // No prefixes provided at all, using constants for context
        assertParseFailure(parser, VALID_MODULE_CODE_CS2103T, expectedMessage);
        assertParseFailure(parser, VALID_TUTORIAL_C123 + " " + VALID_TUTORIAL_T456, expectedMessage);
    }

    @Test
    public void parse_bothPrefixesPresent_success() {
        // Both m/ and t/ prefixes present (now allowed for AND search)
        String userInput = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2103T + " "
                + PREFIX_TUTORIAL_ID + " " + VALID_TUTORIAL_T456;

        // Construct individual predicates
        Predicate<Tutorial> modulePredicate = new ModuleCodeContainsKeywordsPredicate(
                Collections.singletonList(VALID_MODULE_CODE_CS2103T));
        Predicate<Tutorial> tutorialPredicate = new TutorialIdContainsKeywordsPredicate(
                Collections.singletonList(VALID_TUTORIAL_T456));

        // Combine them into the expected AND predicate
        List<Predicate<Tutorial>> predicates = Arrays.asList(tutorialPredicate, modulePredicate);
        ModuleCodeAndTutorialIdContainsKeywordsPredicate expectedCombinedPredicate =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(predicates);

        FindCommand expectedCommand = new FindCommand(expectedCombinedPredicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyKeywordValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

        // m/ prefix present but value is empty
        assertParseFailure(parser, " " + PREFIX_MODULE_CODE, expectedMessage);

        // m/ prefix present but value is only whitespace
        assertParseFailure(parser, " " + PREFIX_MODULE_CODE + " \t ", expectedMessage);

        // t/ prefix present but value is empty
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_ID, expectedMessage);

        // t/ prefix present but value is only whitespace
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_ID + " ", expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // Duplicating the module code prefix
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE_CODE);
        assertParseFailure(parser, " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2103T + " "
                + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_MA1521, expectedMessage);

        // Duplicating the tutorial ID prefix
        expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_ID);
        assertParseFailure(parser, " " + PREFIX_TUTORIAL_ID + " " + VALID_TUTORIAL_C123 + " "
                + PREFIX_TUTORIAL_ID + " " + VALID_TUTORIAL_T456, expectedMessage);
    }
}
