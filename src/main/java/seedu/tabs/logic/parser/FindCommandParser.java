package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.tabs.logic.commands.FindCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.ModuleCodeAndTutorialIdContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.ModuleCodeContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.Tutorial;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAllPrefix(args);

        boolean hasModuleCode = argMultimap.getValue(MODULE_CODE.prefix).isPresent();
        boolean hasTutorialId = argMultimap.getValue(TUTORIAL_ID.prefix).isPresent();

        // Check for missing prefixes
        if (!hasModuleCode && !hasTutorialId) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Check for duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(TUTORIAL_ID.prefix, MODULE_CODE.prefix);

        // Check for extra prefixes
        argMultimap.verifyNoExtraPrefixesExcept(TUTORIAL_ID.prefix, MODULE_CODE.prefix);

        List<Predicate<Tutorial>> predicates = new ArrayList<>();

        // Extract and process keywords based on the present prefix
        if (hasTutorialId) {
            String tutorialIdArgs = argMultimap.getValue(TUTORIAL_ID.prefix).get().trim();
            if (tutorialIdArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            // Split the argument string by one or more whitespace
            String[] idKeywords = tutorialIdArgs.split("\\s+");
            predicates.add(new TutorialIdContainsKeywordsPredicate(Arrays.asList(idKeywords)));
        }
        if (hasModuleCode) { // Must be hasModuleCode based on earlier checks
            String moduleCodeArgs = argMultimap.getValue(MODULE_CODE.prefix).get().trim();
            if (moduleCodeArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            // Split the argument string by one or more whitespace
            String[] moduleKeywords = moduleCodeArgs.split("\\s+");
            predicates.add(new ModuleCodeContainsKeywordsPredicate(Arrays.asList(moduleKeywords)));
        }

        // Combine predicates based on count
        if (predicates.size() == 1) {
            // Only one prefix was given (t/ OR m/)
            return new FindCommand(predicates.get(0));
        } else {
            // Must be size 2, meaning both t/ and m/ were present. Use the AND predicate.
            return new FindCommand(new ModuleCodeAndTutorialIdContainsKeywordsPredicate(predicates));
        }
    }
}
