package seedu.tabs.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tabs.testutil.TutorialBuilder;

public class ModuleCodeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ModuleCodeContainsKeywordsPredicate firstPredicate =
                new ModuleCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        ModuleCodeContainsKeywordsPredicate secondPredicate =
                new ModuleCodeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleCodeContainsKeywordsPredicate firstPredicateCopy =
                new ModuleCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tutorial -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleCodeContainsKeywords_returnsTrue() {
        // One keyword matches Module Code
        ModuleCodeContainsKeywordsPredicate predicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));

        // Multiple keywords, one matching Module Code
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("ZZZ", "CS2103T"));
        assertTrue(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));

        // Mixed-case keywords match (Module Code is CS2103T)
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103t", "zzz"));
        assertTrue(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));

        // Match part of the Module Code
        predicate = new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("2103"));
        assertTrue(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));
    }

    @Test
    public void test_moduleCodeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleCodeContainsKeywordsPredicate predicate =
                new ModuleCodeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));

        // Non-matching keyword
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("ZZZ", "X999"));
        assertFalse(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));

        // Keyword matches Tutorial ID but NOT Module Code (Module Code is CS2103T)
        predicate = new ModuleCodeContainsKeywordsPredicate(Collections.singletonList("T09"));
        assertFalse(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T").build()));

        // Keywords match other non-searched fields (e.g., date)
        predicate = new ModuleCodeContainsKeywordsPredicate(Arrays.asList("2025-01-15", "Main", "Street"));
        assertFalse(predicate.test(new TutorialBuilder().withId("T09").withModuleCode("CS2103T")
                .withDate("2025-01-15").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ModuleCodeContainsKeywordsPredicate predicate = new ModuleCodeContainsKeywordsPredicate(keywords);

        String expected = ModuleCodeContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
