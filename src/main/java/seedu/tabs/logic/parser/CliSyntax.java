package seedu.tabs.logic.parser;

import java.util.stream.Stream;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public enum CliSyntax {
    TUTORIAL_ID(new Prefix("t/")),
    MODULE_CODE(new Prefix("m/")),
    DATE(new Prefix("d/")),
    STUDENT(new Prefix("id/")),
    FROM(new Prefix("from/"));

    public final Prefix prefix;

    CliSyntax(Prefix prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns a stream of all available prefixes.
     */
    public static Stream<Prefix> getAllPrefixesStream() {
        return Stream.of(values()).map(syntax -> syntax.prefix);
    }

    /**
     * Returns an array of all available prefixes.
     */
    public static Prefix[] getAllPrefixes() {
        CliSyntax[] values = values();
        Prefix[] prefixes = new Prefix[values.length];
        for (int i = 0; i < values.length; i++) {
            prefixes[i] = values[i].prefix;
        }
        return prefixes;
    }

    @Override
    public String toString() {
        return prefix.getPrefix();
    }
}
