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

    public static final Tutorial TUTORIAL_CS2103T_C101 = new TutorialBuilder().withName("C101")
            .withAddress("123, Jurong West Ave 6, #08-111").withDate("2025-01-10")
            .withModuleCode("CS2103T")
            .withStudents("friends").build();
    public static final Tutorial TUTORIAL_MA1521_T202 = new TutorialBuilder().withName("T202")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withDate("2025-01-15").withModuleCode("MA1521")
            .withStudents("owesMoney", "friends").build();
    public static final Tutorial TUTORIAL_CS1010_C303 = new TutorialBuilder().withName("C303").withModuleCode("CS1010")
            .withDate("2025-01-20").withAddress("wall street").build();
    public static final Tutorial TUTORIAL_ST2334_T404 = new TutorialBuilder().withName("T404").withModuleCode("ST2334")
            .withDate("2025-01-25").withAddress("10th street").withStudents("friends").build();
    public static final Tutorial TUTORIAL_CS2040_C505 = new TutorialBuilder().withName("C505").withModuleCode("CS2040")
            .withDate("2025-02-01").withAddress("michegan ave").build();
    public static final Tutorial TUTORIAL_MA2001_T606 = new TutorialBuilder().withName("T606").withModuleCode("MA2001")
            .withDate("2025-02-05").withAddress("little tokyo").build();
    public static final Tutorial TUTORIAL_EE2026_C707 = new TutorialBuilder().withName("C707").withModuleCode("EE2026")
            .withDate("2025-02-10").withAddress("4th street").build();

    // Manually added
    public static final Tutorial TUTORIAL_GE1401_T808 = new TutorialBuilder().withName("T808").withModuleCode("GE1401")
            .withDate("2025-02-15").withAddress("little india").build();
    public static final Tutorial TUTORIAL_CS3230_C909 = new TutorialBuilder().withName("C909").withModuleCode("CS3230")
            .withDate("2025-02-20").withAddress("chicago ave").build();

    // Manually added - Tutorial's details found in {@code CommandTestUtil}
    public static final Tutorial TUTORIAL_TEST_C123 = new TutorialBuilder().withName(VALID_NAME_AMY)
            .withModuleCode(VALID_PHONE_AMY).withDate(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY)
            .withStudents(VALID_TAG_FRIEND).build();
    public static final Tutorial TUTORIAL_TEST_T456 = new TutorialBuilder().withName(VALID_NAME_BOB)
            .withModuleCode(VALID_PHONE_BOB).withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB)
            .withStudents(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    // Aliases for backward compatibility with existing tests
    public static final Tutorial ALICE = TUTORIAL_CS2103T_C101;
    public static final Tutorial BOB = TUTORIAL_TEST_T456;
    public static final Tutorial CARL = TUTORIAL_CS1010_C303;
    public static final Tutorial DANIEL = TUTORIAL_ST2334_T404;
    public static final Tutorial ELLE = TUTORIAL_CS2040_C505;
    public static final Tutorial FIONA = TUTORIAL_MA2001_T606;
    public static final Tutorial GEORGE = TUTORIAL_EE2026_C707;
    public static final Tutorial HOON = TUTORIAL_GE1401_T808;
    public static final Tutorial IDA = TUTORIAL_CS3230_C909;
    public static final Tutorial AMY = TUTORIAL_TEST_C123;
    public static final Tutorial BENSON = TUTORIAL_MA1521_T202;

    public static final String KEYWORD_MATCHING_MEIER = "T"; // A keyword that matches tutorials starting with T

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
        return new ArrayList<>(Arrays.asList(TUTORIAL_CS2103T_C101, TUTORIAL_MA1521_T202, TUTORIAL_CS1010_C303,
                TUTORIAL_ST2334_T404, TUTORIAL_CS2040_C505, TUTORIAL_MA2001_T606, TUTORIAL_EE2026_C707));
    }
}
