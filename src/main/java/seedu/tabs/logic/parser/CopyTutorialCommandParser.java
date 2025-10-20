package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_ID, PREFIX_FROM, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_ID, PREFIX_FROM, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CopyTutorialCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_ID, PREFIX_FROM, PREFIX_DATE);
        TutorialId newTutorialId = ParserUtil.parseName(argMultimap.getValue(PREFIX_TUTORIAL_ID).get());
        TutorialId sourceTutorialId = ParserUtil.parseName(argMultimap.getValue(PREFIX_FROM).get());
        Date newDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

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
