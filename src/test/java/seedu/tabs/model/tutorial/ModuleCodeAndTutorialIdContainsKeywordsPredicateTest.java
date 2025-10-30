package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.tabs.testutil.TutorialBuilder;

public class ModuleCodeAndTutorialIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Predicate<Tutorial> predicate1 =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        Predicate<Tutorial> predicate2 =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("T09"));

        ModuleCodeAndTutorialIdContainsKeywordsPredicate first =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(List.of(predicate1));
        ModuleCodeAndTutorialIdContainsKeywordsPredicate second =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(List.of(predicate1, predicate2));

        // same object -> returns true
        assertTrue(first.equals(first));

        // same values -> returns true
        ModuleCodeAndTutorialIdContainsKeywordsPredicate firstCopy =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(List.of(predicate1));
        assertTrue(first.equals(firstCopy));

        // different types -> returns false
        assertFalse(first.equals(1));

        // null -> returns false
        assertFalse(first.equals(null));

        // different predicates list -> returns false
        assertFalse(first.equals(second));
    }

    @Test
    public void test_allPredicatesMatch_returnsTrue() {
        Predicate<Tutorial> modulePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        Predicate<Tutorial> idPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("T09"));

        ModuleCodeAndTutorialIdContainsKeywordsPredicate combined =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(List.of(modulePredicate, idPredicate));

        // Both predicates match
        assertTrue(combined.test(new TutorialBuilder().withModuleCode("CS2103T").withId("T09").build()));
    }

    @Test
    public void test_somePredicatesDoNotMatch_returnsFalse() {
        Predicate<Tutorial> modulePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        Predicate<Tutorial> idPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("T99"));

        ModuleCodeAndTutorialIdContainsKeywordsPredicate combined =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(List.of(modulePredicate, idPredicate));

        // Module code matches, but tutorial ID does not
        assertFalse(combined.test(new TutorialBuilder().withModuleCode("CS2103T").withId("T09").build()));
    }

    @Test
    public void test_allPredicatesDoNotMatch_returnsFalse() {
        Predicate<Tutorial> modulePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS9999"));
        Predicate<Tutorial> idPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("T77"));

        ModuleCodeAndTutorialIdContainsKeywordsPredicate combined =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(List.of(modulePredicate, idPredicate));

        // Neither matches
        assertFalse(combined.test(new TutorialBuilder().withModuleCode("CS2103T").withId("T09").build()));
    }

    @Test
    public void test_emptyPredicateList_returnsTrue() {
        // By definition, allMatch() on an empty stream returns true
        ModuleCodeAndTutorialIdContainsKeywordsPredicate combined =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(Collections.emptyList());

        assertTrue(combined.test(new TutorialBuilder().withModuleCode("CS2103T").withId("T09").build()));
    }

    @Test
    public void toStringMethod() {
        Predicate<Tutorial> modulePredicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        Predicate<Tutorial> idPredicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("T09"));
        List<Predicate<Tutorial>> predicateList = Arrays.asList(modulePredicate, idPredicate);

        ModuleCodeAndTutorialIdContainsKeywordsPredicate combined =
                new ModuleCodeAndTutorialIdContainsKeywordsPredicate(predicateList);

        String expected = ModuleCodeAndTutorialIdContainsKeywordsPredicate.class.getCanonicalName()
                + "{predicates=" + predicateList + "}";
        assertEquals(expected, combined.toString());
    }
}
