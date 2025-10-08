package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TutorialBuilder;

public class ClassIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ClassIdContainsKeywordsPredicate firstPredicate = new ClassIdContainsKeywordsPredicate(firstPredicateKeywordList);
        ClassIdContainsKeywordsPredicate secondPredicate = new ClassIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClassIdContainsKeywordsPredicate firstPredicateCopy = new ClassIdContainsKeywordsPredicate(firstPredicateKeywordList);
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
        ClassIdContainsKeywordsPredicate predicate = new ClassIdContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClassIdContainsKeywordsPredicate predicate = new ClassIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorialBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new TutorialBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ClassIdContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new TutorialBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ClassIdContainsKeywordsPredicate predicate = new ClassIdContainsKeywordsPredicate(keywords);

        String expected = ClassIdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
