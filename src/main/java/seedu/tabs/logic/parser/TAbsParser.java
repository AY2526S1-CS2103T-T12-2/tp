package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tabs.commons.core.LogsCenter;
import seedu.tabs.logic.commands.AddStudentCommand;
import seedu.tabs.logic.commands.AddTutorialCommand;
import seedu.tabs.logic.commands.ClearCommand;
import seedu.tabs.logic.commands.Command;
import seedu.tabs.logic.commands.CopyTutorialCommand;
import seedu.tabs.logic.commands.DeleteStudentCommand;
import seedu.tabs.logic.commands.DeleteTutorialCommand;
import seedu.tabs.logic.commands.EditTutorialCommand;
import seedu.tabs.logic.commands.ExitCommand;
import seedu.tabs.logic.commands.FindCommand;
import seedu.tabs.logic.commands.HelpCommand;
import seedu.tabs.logic.commands.ListCommand;
import seedu.tabs.logic.commands.ListStudentsCommand;
import seedu.tabs.logic.commands.MarkAllCommand;
import seedu.tabs.logic.commands.MarkCommand;
import seedu.tabs.logic.commands.UnmarkAllCommand;
import seedu.tabs.logic.commands.UnmarkCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TAbsParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(TAbsParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddTutorialCommand.COMMAND_WORD:
            return new AddTutorialCommandParser().parse(arguments);

        case CopyTutorialCommand.COMMAND_WORD:
            return new CopyTutorialCommandParser().parse(arguments);

        case EditTutorialCommand.COMMAND_WORD:
            return new EditTutorialCommandParser().parse(arguments);

        case DeleteTutorialCommand.COMMAND_WORD:
            return new DeleteTutorialCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListStudentsCommand.COMMAND_WORD:
            return new ListStudentsCommandParser().parse(arguments);

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        case MarkAllCommand.COMMAND_WORD:
            return new MarkAllCommandParser().parse(arguments);

        case UnmarkAllCommand.COMMAND_WORD:
            return new UnmarkAllCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
