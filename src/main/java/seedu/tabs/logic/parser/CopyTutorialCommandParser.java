package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.FROM;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import java.util.stream.Stream;

import seedu.tabs.logic.commands.CopyTutorialCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Parses input arguments and creates a new CopyTutorialCommand object
 */
public class CopyTutorialCommandParser implements Parser<CopyTutorialCommand> {

    /**
     * Parses the given String of arguments in the context of the CopyTutorialCommand
     * and returns a CopyTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAllPrefix(args);

        if (!arePrefixesPresent(argMultimap, TUTORIAL_ID.prefix, FROM.prefix, DATE.prefix)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CopyTutorialCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(TUTORIAL_ID.prefix, FROM.prefix, DATE.prefix);
        argMultimap.verifyNoExtraPrefixesExcept(TUTORIAL_ID.prefix, FROM.prefix, DATE.prefix);
        TutorialId newTutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(TUTORIAL_ID.prefix).get());
        TutorialId sourceTutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(FROM.prefix).get());
        Date newDate = ParserUtil.parseDate(argMultimap.getValue(DATE.prefix).get());

        return new CopyTutorialCommand(newTutorialId, sourceTutorialId, newDate);
    }

    /**
     * Returns true if none of the prefixes contains empty Optional values in the given
     * ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
