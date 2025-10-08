package seedu.tabs.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tabs.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.tabs.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tabs.testutil.Assert.assertThrows;
import static seedu.tabs.testutil.TypicalTutorials.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tabs.logic.commands.AddCommand;
import seedu.tabs.logic.commands.CommandResult;
import seedu.tabs.logic.commands.ListCommand;
import seedu.tabs.logic.commands.exceptions.CommandException;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.Model;
import seedu.tabs.model.ModelManager;
import seedu.tabs.model.ReadOnlyTAbs;
import seedu.tabs.model.UserPrefs;
import seedu.tabs.model.tutorial.Tutorial;
import seedu.tabs.storage.JsonTAbsStorage;
import seedu.tabs.storage.JsonUserPrefsStorage;
import seedu.tabs.storage.StorageManager;
import seedu.tabs.testutil.TutorialBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonTAbsStorage tabsStorage =
                new JsonTAbsStorage(temporaryFolder.resolve("tabs.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(tabsStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    //    @Test
    //    public void execute_commandExecutionError_throwsCommandException() {
    //        String deleteCommand = "delete_tutorial t/9";
    //        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredTutorialList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTutorialList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, java.lang.Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, java.lang.Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, java.lang.Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, java.lang.Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, java.lang.Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getTAbs(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, java.lang.Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e               the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an TAbsStorage that throws the IOException e when saving
        JsonTAbsStorage tabsStorage = new JsonTAbsStorage(prefPath) {
            @Override
            public void saveTAbs(ReadOnlyTAbs tabs, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(tabsStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveTAbs method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + DATE_DESC_AMY + ADDRESS_DESC_AMY;
        Tutorial expectedTutorial = new TutorialBuilder(AMY).withStudents().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTutorial(expectedTutorial);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
