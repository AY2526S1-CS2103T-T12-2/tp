package seedu.tabs.model.tutorial;

import java.util.function.Predicate;

import seedu.tabs.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Tutorial}'s {@code ID} matches the given keyword exactly (case sensitive).
 */
public class TutorialIdMatchesKeywordPredicate implements Predicate<Tutorial> {

    private final String keyword;

    public TutorialIdMatchesKeywordPredicate(String keyword) { this.keyword = keyword; }

    @Override
    public boolean test(Tutorial aTutorial) {
        return keyword.equals(aTutorial.getTutorialId().fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialIdMatchesKeywordPredicate)) {
            return false;
        }

        TutorialIdMatchesKeywordPredicate otherTutorialIdMatchesKeywordPredicate =
                (TutorialIdMatchesKeywordPredicate) other;
        return keyword.equals(otherTutorialIdMatchesKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
