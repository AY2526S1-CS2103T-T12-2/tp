package seedu.tabs.testutil;

import seedu.tabs.model.tutorial.TutorialIdMatchesKeywordPredicate;

/**
 * A utility class containing a list of {@code TutorialMatchesKeywordPredicate} objects to be used in tests.
 */
public class TypicalPredicates {
    public static final TutorialIdMatchesKeywordPredicate PREDICATE_KEYWORD_C101 =
            new TutorialIdMatchesKeywordPredicate("C101");
    public static final TutorialIdMatchesKeywordPredicate PREDICATE_KEYWORD_C102 =
            new TutorialIdMatchesKeywordPredicate("C102");
    public static final TutorialIdMatchesKeywordPredicate PREDICATE_KEYWORD_C123 =
            new TutorialIdMatchesKeywordPredicate("C123");
}
