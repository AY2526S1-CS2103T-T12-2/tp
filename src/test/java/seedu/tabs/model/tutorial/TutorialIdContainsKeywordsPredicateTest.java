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
                new TutorialIdContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TutorialIdContainsKeywordsPredicate predicate =
                new TutorialIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorialBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));

        // Keywords match moduleCode, date and tabs, but does not match name
        predicate = new TutorialIdContainsKeywordsPredicate(Arrays.asList("12345", "alice@date.com", "Main", "Street"));
        assertFalse(predicate.test(new TutorialBuilder().withName("Alice").withModuleCode("12345")
                .withEmail("alice@date.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TutorialIdContainsKeywordsPredicate predicate = new TutorialIdContainsKeywordsPredicate(keywords);

        String expected = TutorialIdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
