package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.stream.Stream;

import seedu.tabs.logic.commands.MarkAllCommand;
import seedu.tabs.logic.commands.UnmarkAllCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Parses input argument and creates a new {@code MarkAllCommand} object.
 */
public class UnmarkAllCommandParser implements Parser<UnmarkAllCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkAllCommand}
     * and returns a {@code MarkAllCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public UnmarkAllCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAllCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_ID);
        TutorialId tutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(PREFIX_TUTORIAL_ID).get());
        return new UnmarkAllCommand(new TutorialIdMatchesKeywordPredicate(tutorialId.id));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
