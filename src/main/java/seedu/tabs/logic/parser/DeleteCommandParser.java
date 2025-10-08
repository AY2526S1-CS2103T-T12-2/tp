package seedu.tabs.logic.parser;

import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.logic.commands.DeleteTutorialCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTutorialCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialCommand.MESSAGE_USAGE));
        }

        return new DeleteTutorialCommand(new TutorialIdMatchesKeywordPredicate(trimmedArgs));
    }

}
