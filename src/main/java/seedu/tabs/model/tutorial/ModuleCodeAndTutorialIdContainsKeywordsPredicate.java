package seedu.tabs.model.tutorial;

import java.util.List;
import java.util.function.Predicate;

import seedu.tabs.commons.util.ToStringBuilder;

/**
 * Tests if a {@code Tutorial} satisfies all predicates in a given list (AND condition).
 */
public class ModuleCodeAndTutorialIdContainsKeywordsPredicate implements Predicate<Tutorial> {

    private final List<Predicate<Tutorial>> predicates;

    /**
     * Constructs an ModuleCodeAndTutorialCodeContainsKeywordsPredicate with the given list of predicates.
     * The list should not be null, and should typically contain at least one predicate.
     */
    public ModuleCodeAndTutorialIdContainsKeywordsPredicate(List<Predicate<Tutorial>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Tutorial aTutorial) {
        // A tutorial must satisfy ALL predicates (AND condition).
        return predicates.stream().allMatch(predicate -> predicate.test(aTutorial));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCodeAndTutorialIdContainsKeywordsPredicate)) {
            return false;
        }

        ModuleCodeAndTutorialIdContainsKeywordsPredicate otherModuleCodeAndTutorialIdContainsKeywordsPredicate =
                (ModuleCodeAndTutorialIdContainsKeywordsPredicate) other;
        // Two predicates are equal if they contain the same list of predicates
        // in the same order (though order may not strictly matter for AND, comparing the list
        // ensures consistency).
        return predicates.equals(otherModuleCodeAndTutorialIdContainsKeywordsPredicate.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", predicates)
                .toString();
    }
}
