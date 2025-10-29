package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import java.util.stream.Stream;

import seedu.tabs.logic.commands.DeleteTutorialCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Parses input argument and creates a new {@code DeleteTutorialCommand} object.
 */
public class DeleteTutorialCommandParser implements Parser<DeleteTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteTutorialCommand}
     * and returns a {@code DeleteTutorialCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAllPrefix(args);

        if (!arePrefixesPresent(argMultimap, TUTORIAL_ID.prefix) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTutorialCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(TUTORIAL_ID.prefix);
        argMultimap.verifyNoExtraPrefixesExcept(TUTORIAL_ID.prefix);
        TutorialId tutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(TUTORIAL_ID.prefix).get());
        return new DeleteTutorialCommand(new TutorialIdMatchesKeywordPredicate(tutorialId.id));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
