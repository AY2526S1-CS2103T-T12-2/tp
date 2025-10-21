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
        // One keyword
        TutorialIdContainsKeywordsPredicate predicate =
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("C123"));
        assertTrue(predicate.test(new TutorialBuilder().withId("C123").build()));

        // Multiple keywords
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("C123", "T456"));
        assertTrue(predicate.test(new TutorialBuilder().withId("C123").build()));

        // Only one matching keyword
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("T456", "CT789"));
        assertTrue(predicate.test(new TutorialBuilder().withId("CT789").build()));

        // Mixed-case keywords
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("c123", "t456"));
        assertTrue(predicate.test(new TutorialBuilder().withId("C123").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TutorialIdContainsKeywordsPredicate predicate =
                new TutorialIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorialBuilder().withId("C123").build()));

        // Non-matching keyword
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("T999"));
        assertFalse(predicate.test(new TutorialBuilder().withId("C123").build()));

        // Keywords match moduleCode and date but does not match name
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("CS2103T", "2025-01-15", "Main", "Street"));
        assertFalse(predicate.test(new TutorialBuilder().withId("C123").withModuleCode("CS2103T")
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
