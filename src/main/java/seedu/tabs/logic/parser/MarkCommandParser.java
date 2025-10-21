package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import seedu.tabs.logic.commands.MarkCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT, PREFIX_TUTORIAL_ID);

        boolean hasMissingPrefixes = !arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_TUTORIAL_ID);
        boolean hasNonEmptyPreamble = !argMultimap.getPreamble().isEmpty();
        if (hasMissingPrefixes || hasNonEmptyPreamble) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_ID);
        Set<Student> studentSet = ParserUtil.parseStudents(argMultimap.getAllValues(PREFIX_STUDENT));

        String tutorialId;
        try {
            tutorialId = argMultimap.getValue(PREFIX_TUTORIAL_ID).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(studentSet, new TutorialIdMatchesKeywordPredicate(tutorialId));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
