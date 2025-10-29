package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tabs.logic.parser.CliSyntax.DATE;
import static seedu.tabs.logic.parser.CliSyntax.FROM;
import static seedu.tabs.logic.parser.CliSyntax.MODULE_CODE;
import static seedu.tabs.logic.parser.CliSyntax.STUDENT;
import static seedu.tabs.logic.parser.CliSyntax.TUTORIAL_ID;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeAllPrefix(args);

        String tutorialId;
        try {
            tutorialId = argMultimap.getValue(FROM.prefix).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(EditTutorialCommand.MESSAGE_FROM_TUTORIAL_ID_MISSING,
                    EditTutorialCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(FROM.prefix, TUTORIAL_ID.prefix, MODULE_CODE.prefix, DATE.prefix);
        argMultimap.verifyNoExtraPrefixesExcept(FROM.prefix, TUTORIAL_ID.prefix, MODULE_CODE.prefix, DATE.prefix,
                STUDENT.prefix);
        EditTutorialDescriptor editTutorialDescriptor = new EditTutorialDescriptor();

        if (argMultimap.getValue(TUTORIAL_ID.prefix).isPresent()) {
            editTutorialDescriptor.setId(ParserUtil.parseTutorialId(argMultimap.getValue(TUTORIAL_ID.prefix).get()));
        }
        if (argMultimap.getValue(MODULE_CODE.prefix).isPresent()) {
            editTutorialDescriptor.setModuleCode(ParserUtil.parseModuleCode(
                    argMultimap.getValue(MODULE_CODE.prefix).get()));
        }
        if (argMultimap.getValue(DATE.prefix).isPresent()) {
            editTutorialDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(DATE.prefix).get()));
        }

        if (argMultimap.getValue(STUDENT.prefix).isPresent()) {
            throw new ParseException(EditTutorialCommand.MESSAGE_EDIT_STUDENTS_NOT_ALLOWED);
        }

        if (!editTutorialDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTutorialCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTutorialCommand(
                new TutorialIdMatchesKeywordPredicate(tutorialId.toUpperCase()), editTutorialDescriptor);
    }
}
