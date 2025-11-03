package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

import java.util.Set;
import java.util.stream.Stream;

import seedu.tabs.logic.commands.AddTutorialCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Parses input arguments and creates a new AddTutorialCommand object
 */
public class AddTutorialCommandParser implements Parser<AddTutorialCommand> {

    /**
     * Parses the given String of arguments in the context of the AddTutorialCommand
     * and returns an AddTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAllPrefix(args);

        if (!arePrefixesPresent(argMultimap, TUTORIAL_ID.prefix, MODULE_CODE.prefix, DATE.prefix)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(TUTORIAL_ID.prefix, MODULE_CODE.prefix, DATE.prefix);
        argMultimap.verifyNoExtraPrefixesExcept(TUTORIAL_ID.prefix, MODULE_CODE.prefix, DATE.prefix, STUDENT.prefix);
        TutorialId tutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(TUTORIAL_ID.prefix).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(MODULE_CODE.prefix).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(DATE.prefix).get());

        // Students are optional - use empty set if not provided
        Set<Student> studentList = ParserUtil.parseStudents(argMultimap.getAllValues(STUDENT.prefix));

        try {
            Tutorial tutorial = new Tutorial(tutorialId, moduleCode, date, studentList);
            return new AddTutorialCommand(tutorial);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty Optional values in the given
     * ArgumentMultimap.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


