package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.NoSuchElementException;

import seedu.tabs.logic.commands.EditTutorialCommand;
import seedu.tabs.logic.commands.EditTutorialCommand.EditTutorialDescriptor;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new EditTutorialCommand object
 */
public class EditTutorialCommandParser implements Parser<EditTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTutorialCommand
     * and returns an EditTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTutorialCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_TUTORIAL_ID, PREFIX_MODULE_CODE,
                        PREFIX_DATE, PREFIX_STUDENT);

        String tutorialId;
        try {
            tutorialId = argMultimap.getValue(PREFIX_FROM).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(EditTutorialCommand.MESSAGE_FROM_TUTORIAL_ID_MISSING,
                    EditTutorialCommand.MESSAGE_USAGE));
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

        if (argMultimap.getValue(PREFIX_STUDENT).isPresent()) {
            throw new ParseException(EditTutorialCommand.MESSAGE_EDIT_STUDENTS_NOT_ALLOWED);
        }

        if (!editTutorialDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTutorialCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTutorialCommand(new TutorialIdMatchesKeywordPredicate(tutorialId), editTutorialDescriptor);
    }
}
