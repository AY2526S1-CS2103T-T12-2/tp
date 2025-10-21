package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tabs.testutil.TutorialBuilder;

public class TutorialIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TutorialIdContainsKeywordsPredicate firstPredicate =
                new TutorialIdContainsKeywordsPredicate(firstPredicateKeywordList);
        TutorialIdContainsKeywordsPredicate secondPredicate =
                new TutorialIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TutorialIdContainsKeywordsPredicate firstPredicateCopy =
                new TutorialIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tutorial -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword matches Tutorial ID/Name
        TutorialIdContainsKeywordsPredicate predicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("T09"));
        assertTrue(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T").build()));

        // One keyword matches Module Code
        predicate = new TutorialIdContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T").build()));

        // Multiple keywords, only one matching (in Module Code)
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("T456", "CS2103T"));
        assertTrue(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T").build()));

        // Mixed-case keywords match (Module Code)
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("cs2103t", "t456"));
        assertTrue(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TutorialIdContainsKeywordsPredicate predicate =
                new TutorialIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T").build()));

        // Non-matching keyword
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("ZZZ", "X999"));
        assertFalse(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T").build()));

        // Keywords match other non-searched fields (e.g., date) but not ID or Module Code
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("2025-01-15", "Main", "Street"));
        assertFalse(predicate.test(new TutorialBuilder().withName("T09").withModuleCode("CS2103T")
                .withDate("2025-01-15").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TutorialIdContainsKeywordsPredicate predicate = new TutorialIdContainsKeywordsPredicate(keywords);

        String expected = TutorialIdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
