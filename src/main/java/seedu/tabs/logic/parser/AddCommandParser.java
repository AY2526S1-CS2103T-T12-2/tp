package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.Set;
import java.util.stream.Stream;

import seedu.tabs.logic.commands.AddCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Address;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_ID, PREFIX_MODULE_CODE, PREFIX_DATE,
                        PREFIX_ADDRESS, PREFIX_STUDENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_ID, PREFIX_ADDRESS, PREFIX_MODULE_CODE, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_ID, PREFIX_MODULE_CODE, PREFIX_DATE, PREFIX_ADDRESS);
        TutorialId tutorialId = ParserUtil.parseName(argMultimap.getValue(PREFIX_TUTORIAL_ID).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Date date = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_DATE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Student> studentList = ParserUtil.parseStudents(argMultimap.getAllValues(PREFIX_STUDENT));

        Tutorial aTutorial = new Tutorial(tutorialId, moduleCode, date, address, studentList);

        return new AddCommand(aTutorial);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
