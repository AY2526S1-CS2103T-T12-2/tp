package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.logic.commands.DeleteStudentCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object
 */
public class DeleteStudentCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStudentCommand
     * and returns an DeleteStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteStudentCommand.MESSAGE_USAGE), ive);
        }

        String studentId = argMultimap.getValue(PREFIX_STUDENT).orElse("");
        Student student = ParserUtil.parseStudent(studentId);

        return new DeleteStudentCommand(index, student);
    }
}
