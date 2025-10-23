package seedu.tabs.model.tutorial;

import java.util.List;
import java.util.function.Predicate;

import seedu.tabs.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Tutorial}'s {@code ModuleCode} matches any of the keywords given.
 */
public class ModuleCodeContainsKeywordsPredicate implements Predicate<Tutorial> {
    private final List<String> keywords;

    public ModuleCodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tutorial aTutorial) {
        return keywords.stream().anyMatch(keyword -> aTutorial.getModuleCode().value
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCodeContainsKeywordsPredicate)) {
            return false;
        }

        ModuleCodeContainsKeywordsPredicate otherModuleCodecontainsKeywordsPredicate =
                (ModuleCodeContainsKeywordsPredicate) other;
        return keywords.equals(otherModuleCodecontainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
