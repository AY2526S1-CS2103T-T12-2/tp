package seedu.tabs.testutil;

import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tabs.model.TAbs;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * A utility class containing a list of {@code Tutorial} objects to be used in tests.
 */
public class TypicalTutorials {

    public static final Tutorial ALICE = new TutorialBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withModuleCode("94351253")
            .withStudents("friends").build();
    public static final Tutorial BENSON = new TutorialBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withModuleCode("98765432")
            .withStudents("owesMoney", "friends").build();
    public static final Tutorial CARL = new TutorialBuilder().withName("Carl Kurz").withModuleCode("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Tutorial DANIEL = new TutorialBuilder().withName("Daniel Meier").withModuleCode("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withStudents("friends").build();
    public static final Tutorial ELLE = new TutorialBuilder().withName("Elle Meyer").withModuleCode("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Tutorial FIONA = new TutorialBuilder().withName("Fiona Kunz").withModuleCode("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Tutorial GEORGE = new TutorialBuilder().withName("George Best").withModuleCode("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Tutorial HOON = new TutorialBuilder().withName("Hoon Meier").withModuleCode("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Tutorial IDA = new TutorialBuilder().withName("Ida Mueller").withModuleCode("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Tutorial's details found in {@code CommandTestUtil}
    public static final Tutorial AMY = new TutorialBuilder().withName(VALID_NAME_AMY).withModuleCode(VALID_PHONE_AMY)
            .withEmail(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY).withStudents(VALID_TAG_FRIEND).build();
    public static final Tutorial BOB = new TutorialBuilder().withName(VALID_NAME_BOB).withModuleCode(VALID_PHONE_BOB)
            .withEmail(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB).withStudents(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTutorials() {
    } // prevents instantiation

    /**
     * Returns an {@code TAbs} with all the typical tutorials.
     */
    public static TAbs getTypicalTAbs() {
        TAbs ab = new TAbs();
        for (Tutorial aTutorial : getTypicalTutorials()) {
            ab.addTutorial(aTutorial);
        }
        return ab;
    }

    public static List<Tutorial> getTypicalTutorials() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
