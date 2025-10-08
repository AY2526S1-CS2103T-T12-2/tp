package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tabs.logic.commands.DeleteCommand;
import seedu.tabs.logic.commands.ListStudentsCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ListStudentsCommandParser implements Parser<ListStudentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListStudentsCommand parse(String args) throws ParseException {
        try {
            TutorialId tutorialId = ParserUtil.parseName(args);
            return new ListStudentsCommand(tutorialId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
