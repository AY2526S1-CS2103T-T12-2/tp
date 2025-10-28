
package seedu.tabs.testutil;

import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_DATE_T456;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_MODULE_CODE_MA1521;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_A;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_STUDENT_B;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_C123;
import static seedu.tabs.logic.commands.CommandTestUtil.VALID_TUTORIAL_T456;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tabs.model.TAbs;
import seedu.tabs.model.tutorial.Tutorial;

/**
 * A utility class containing a list of {@code Tutorial} objects to be used in tests.
 */
public class TypicalTutorials {

    public static final Tutorial TUTORIAL_CS2103T_A101 = new TutorialBuilder().withId("A101")
            .withDate("2025-01-10")
            .withModuleCode("CS2103T")
            .withStudents(VALID_STUDENT_A).build();
    public static final Tutorial TUTORIAL_MA1521_B202 = new TutorialBuilder().withId("B202")
            .withDate("2025-01-15").withModuleCode("MA1521")
            .withStudents(VALID_STUDENT_A).build();
    public static final Tutorial TUTORIAL_CS1010_C303 = new TutorialBuilder().withId("C303").withModuleCode("CS1010")
            .withDate("2025-01-20")
            .withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();;
    public static final Tutorial TUTORIAL_ST2334_D404 = new TutorialBuilder().withId("D404").withModuleCode("ST2334")
            .withDate("2025-01-25").withStudents(VALID_STUDENT_A).build();
    public static final Tutorial TUTORIAL_CS2040_E505 = new TutorialBuilder().withId("E505").withModuleCode("CS2040")
            .withDate("2025-02-01").build();
    public static final Tutorial TUTORIAL_MA2001_F606 = new TutorialBuilder().withId("F606").withModuleCode("MA2001")
            .withDate("2025-02-05").build();
    public static final Tutorial TUTORIAL_EE2026_G707 = new TutorialBuilder().withId("G707").withModuleCode("EE2026")
            .withDate("2025-02-10").build();

    // Manually added
    public static final Tutorial TUTORIAL_GE1401_H808 = new TutorialBuilder().withId("H808").withModuleCode("GE1401")
            .withDate("2025-02-15").build();
    public static final Tutorial TUTORIAL_CS3230_G909 = new TutorialBuilder().withId("I909").withModuleCode("CS3230")
            .withDate("2025-02-20").build();

    // Manually added - Tutorial's details found in {@code CommandTestUtil}
    public static final Tutorial TUTORIAL_TEST_C123 = new TutorialBuilder().withId(VALID_TUTORIAL_C123)
            .withModuleCode(VALID_MODULE_CODE_CS2103T).withDate(VALID_DATE_C123)
            .withStudents(VALID_STUDENT_B).build();
    public static final Tutorial TUTORIAL_TEST_T456 = new TutorialBuilder().withId(VALID_TUTORIAL_T456)
            .withModuleCode(VALID_MODULE_CODE_MA1521).withDate(VALID_DATE_T456)
            .withStudents(VALID_STUDENT_A, VALID_STUDENT_B).build();

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
        return new ArrayList<>(Arrays.asList(TUTORIAL_CS2103T_A101, TUTORIAL_MA1521_B202, TUTORIAL_CS1010_C303,
                TUTORIAL_ST2334_D404, TUTORIAL_CS2040_E505, TUTORIAL_MA2001_F606, TUTORIAL_EE2026_G707));
    }
}
