package seedu.tabs.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tabs.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tabs.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.tabs.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.tabs.logic.commands.AddTutorialCommand;
import seedu.tabs.logic.commands.ClearCommand;
import seedu.tabs.logic.commands.EditTutorialCommand;
import seedu.tabs.logic.commands.EditTutorialCommand.EditTutorialDescriptor;
import seedu.tabs.logic.commands.ExitCommand;
import seedu.tabs.logic.commands.FindCommand;
import seedu.tabs.logic.commands.HelpCommand;
import seedu.tabs.logic.commands.ListCommand;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.model.tutorial.TutorialIdContainsKeywordsPredicate;
import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;
import seedu.tabs.testutil.EditTutorialDescriptorBuilder;
import seedu.tabs.testutil.TutorialBuilder;
import seedu.tabs.testutil.TutorialUtil;

public class TAbsParserTest {

    private final TAbsParser parser = new TAbsParser();

    @Test
    public void parseCommand_add() throws Exception {
        Tutorial aTutorial = new TutorialBuilder().build();
        AddTutorialCommand command = (AddTutorialCommand) parser.parseCommand(TutorialUtil.getAddCommand(aTutorial));
        assertEquals(new AddTutorialCommand(aTutorial), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    //    @Test
    //    public void parseCommand_delete() throws Exception {
    //        DeleteTutorialCommand command = (DeleteTutorialCommand) parser.parseCommand(
    //                DeleteTutorialCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
    //        assertEquals(new DeleteTutorialCommand(INDEX_FIRST_PERSON), command);
    //    }

    @Test
    public void parseCommand_edit() throws Exception {
        Tutorial aTutorial = new TutorialBuilder().build();
        EditTutorialDescriptor descriptor = new EditTutorialDescriptorBuilder(aTutorial).build();

        String commandString = EditTutorialCommand.COMMAND_WORD + " "
                + CliSyntax.FROM + aTutorial.getTutorialId().id + " "
                + TutorialUtil.getEditTutorialDescriptorDetails(descriptor);

        EditTutorialCommand command =
                (EditTutorialCommand) parser.parseCommand(commandString);

        assertEquals(
                new EditTutorialCommand(
                        new TutorialIdMatchesKeywordPredicate(aTutorial.getTutorialId().id),
                        descriptor),
                command
        );
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("C0", "T01", "C2");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + CliSyntax.TUTORIAL_ID
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TutorialIdContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
