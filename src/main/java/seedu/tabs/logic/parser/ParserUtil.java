package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.util.StringUtil;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]+$";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String MESSAGE_INVALID_KEYWORD =
            "Keywords should only contain alphanumeric characters (letters and digits).";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String tutorialId} into a {@code TutorialId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code TutorialId} is invalid.
     */
    public static TutorialId parseTutorialId(String tutorialId) throws ParseException {
        requireNonNull(tutorialId);
        String trimmedId = tutorialId.trim();
        if (!TutorialId.isValidTutorialId(trimmedId)) {
            throw new ParseException(TutorialId.MESSAGE_CONSTRAINTS);
        }
        return new TutorialId(trimmedId);
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String student} into a {@code Student}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student} is invalid.
     */
    public static Student parseStudent(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudent = studentId.trim();
        if (!Student.isValidStudentId(trimmedStudent)) {
            throw new ParseException(Student.MESSAGE_CONSTRAINTS);
        }
        return new Student(trimmedStudent);
    }

    /**
     * Parses {@code Collection<String> students} into a {@code Set<Student>}.
     */
    public static Set<Student> parseStudents(Collection<String> studentIds) throws ParseException {
        requireNonNull(studentIds);
        final Set<Student> studentSet = new HashSet<>();
        for (String studentId : studentIds) {
            studentSet.add(parseStudent(studentId));
        }
        return studentSet;
    }

    /**
     * Validates that all given keywords are alphanumeric.
     *
     * @param keywords array of keywords to validate.
     * @throws ParseException if any keyword contains non-alphanumeric characters.
     */
    public static void validateAlphanumericKeywords(String[] keywords) throws ParseException {
        requireNonNull(keywords);
        for (String keyword : keywords) {
            if (!keyword.matches(ALPHANUMERIC_PATTERN)) {
                throw new ParseException(MESSAGE_INVALID_KEYWORD);
            }
        }
    }
}
