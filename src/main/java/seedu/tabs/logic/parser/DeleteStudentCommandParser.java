package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import java.util.stream.Stream;

import seedu.tabs.logic.commands.DeleteStudentCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.TutorialId;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object.
 */
public class DeleteStudentCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStudentCommand
     * and returns an DeleteStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAllPrefix(args);

        boolean hasMissingPrefixes = !arePrefixesPresent(argMultimap, STUDENT.prefix, TUTORIAL_ID.prefix);
        boolean hasNonEmptyPreamble = !argMultimap.getPreamble().isEmpty();
        if (hasMissingPrefixes || hasNonEmptyPreamble) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteStudentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(STUDENT.prefix, TUTORIAL_ID.prefix);
        argMultimap.verifyNoExtraPrefixesExcept(STUDENT.prefix, TUTORIAL_ID.prefix);

        String studentId = argMultimap.getValue(STUDENT.prefix).orElse("");
        Student student = ParserUtil.parseStudent(studentId);
        String tutorialId = argMultimap.getValue(TUTORIAL_ID.prefix).orElse("");
        TutorialId parsedTutorialId = ParserUtil.parseTutorialId(tutorialId);
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate(parsedTutorialId.id);

        return new DeleteStudentCommand(predicate, student);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
