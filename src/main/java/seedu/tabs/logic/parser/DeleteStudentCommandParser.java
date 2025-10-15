package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.stream.Stream;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.logic.commands.AddStudentCommand;
import seedu.tabs.logic.commands.DeleteStudentCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT, PREFIX_TUTORIAL_ID);

        boolean hasMissingPrefixes = !arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_TUTORIAL_ID);
        boolean hasNonEmptyPreamble = !argMultimap.getPreamble().isEmpty();
        if (hasMissingPrefixes || hasNonEmptyPreamble) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteStudentCommand.MESSAGE_USAGE));
        }

        String studentId = argMultimap.getValue(PREFIX_STUDENT).orElse("");
        String tutorialId = argMultimap.getValue(PREFIX_TUTORIAL_ID).orElse("");
        Student student = ParserUtil.parseStudent(studentId);
        TutorialIdMatchesKeywordPredicate predicate = new TutorialIdMatchesKeywordPredicate(tutorialId);
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
