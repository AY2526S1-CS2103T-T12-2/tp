package seedu.tabs.model.tutorial;

import java.util.List;
import java.util.function.Predicate;

import seedu.tabs.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Tutorial}'s {@code id} matches any of the keywords given.
 */
public class TutorialIdContainsKeywordsPredicate implements Predicate<Tutorial> {
    private final List<String> keywords;

    public TutorialIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutorial aTutorial) {
        boolean matchesModuleCode = keywords.stream()
                .anyMatch(keyword -> aTutorial.getModuleCode().value
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
        boolean matchesTutorialId = keywords.stream()
                .anyMatch(keyword -> aTutorial.getTutorialId().id
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
        return matchesTutorialId || matchesModuleCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialIdContainsKeywordsPredicate)) {
            return false;
        }

        TutorialIdContainsKeywordsPredicate otherTutorialIdContainsKeywordsPredicate =
                (TutorialIdContainsKeywordsPredicate) other;
        return keywords.equals(otherTutorialIdContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
