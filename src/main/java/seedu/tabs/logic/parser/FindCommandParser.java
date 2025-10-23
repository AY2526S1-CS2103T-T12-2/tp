package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.Arrays;

import seedu.tabs.logic.commands.FindCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.ModuleCodeContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        // Tokenize the input string based on the allowed prefixes
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_TUTORIAL_ID);

        boolean hasModuleCode = argMultimap.getValue(PREFIX_MODULE_CODE).isPresent();
        boolean hasTutorialId = argMultimap.getValue(PREFIX_TUTORIAL_ID).isPresent();

        // 1. Check for missing prefixes
        if (!hasModuleCode && !hasTutorialId) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // 2. Check for duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_ID, PREFIX_MODULE_CODE);

        // 3. Ensure only one type of search is performed
        if (hasModuleCode && hasTutorialId) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // 4. Extract and process keywords based on the present prefix
        if (hasTutorialId) {
            String tutorialIdArgs = argMultimap.getValue(PREFIX_TUTORIAL_ID).get().trim();
            if (tutorialIdArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            // Split the argument string by one or more whitespace
            String[] idKeywords = tutorialIdArgs.split("\\s+");
            return new FindCommand(new TutorialIdContainsKeywordsPredicate(Arrays.asList(idKeywords)));
        } else { // Must be hasModuleCode based on earlier checks
            String moduleCodeArgs = argMultimap.getValue(PREFIX_MODULE_CODE).get().trim();
            if (moduleCodeArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            // Split the argument string by one or more whitespace
            String[] moduleKeywords = moduleCodeArgs.split("\\s+");
            return new FindCommand(new ModuleCodeContainsKeywordsPredicate(Arrays.asList(moduleKeywords)));
        }
    }
}
