package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import seedu.tabs.logic.commands.DeleteTutorialCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTutorialCommand.MESSAGE_USAGE));
        }

        String tutorialId;
        try {
            tutorialId = argMultimap.getValue(PREFIX_TUTORIAL_ID).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTutorialCommand.MESSAGE_USAGE));
        }

        return new DeleteTutorialCommand(new TutorialIdMatchesKeywordPredicate(tutorialId));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
