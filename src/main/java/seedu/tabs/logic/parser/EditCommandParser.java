package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.commands.EditCommand;
import seedu.tabs.logic.commands.EditCommand.EditTutorialDescriptor;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_ID, PREFIX_MODULE_CODE, PREFIX_DATE,
                        PREFIX_STUDENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_ID, PREFIX_MODULE_CODE, PREFIX_DATE);

        EditTutorialDescriptor editTutorialDescriptor = new EditTutorialDescriptor();

        if (argMultimap.getValue(PREFIX_TUTORIAL_ID).isPresent()) {
            editTutorialDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_TUTORIAL_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editTutorialDescriptor.setModuleCode(ParserUtil.parseModuleCode(
                    argMultimap.getValue(PREFIX_MODULE_CODE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editTutorialDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        parseStudentsForEdit(argMultimap.getAllValues(PREFIX_STUDENT)).ifPresent(editTutorialDescriptor::setStudents);

        if (!editTutorialDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTutorialDescriptor);
    }

    /**
     * Parses {@code Collection<String> students} into a {@code Set<Student>} if {@code students} is non-empty.
     * If {@code students} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Student>} containing zero students.
     */
    private Optional<Set<Student>> parseStudentsForEdit(Collection<String> students) throws ParseException {
        assert students != null;

        if (students.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> studentSet = students.size() == 1 && students.contains("") ? Collections.emptySet() : students;
        return Optional.of(ParserUtil.parseStudents(studentSet));
    }

}
