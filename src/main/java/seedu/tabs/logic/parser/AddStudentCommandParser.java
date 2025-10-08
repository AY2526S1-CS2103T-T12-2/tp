package seedu.tabs.logic.parser;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.exceptions.IllegalValueException;
import seedu.tabs.logic.commands.AddStudentCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;

public class AddStudentCommandParser {
    public AddStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddStudentCommand.MESSAGE_USAGE), ive);
        }

        String student_id = argMultimap.getValue(PREFIX_STUDENT).orElse("");

        return new AddStudentCommand(index, new Student(student_id));
    }
}
