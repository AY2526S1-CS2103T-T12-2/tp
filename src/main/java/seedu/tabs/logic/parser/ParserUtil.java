package seedu.tabs.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.tabs.commons.core.index.Index;
import seedu.tabs.commons.util.StringUtil;
import seedu.tabs.logic.parser.exceptions.ParseException;
import seedu.tabs.model.student.Student;
import seedu.tabs.model.tutorial.Address;
import seedu.tabs.model.tutorial.Date;
import seedu.tabs.model.tutorial.ModuleCode;
import seedu.tabs.model.tutorial.TutorialId;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TutorialId parseTutorialId(String tutorialId) throws ParseException {
        requireNonNull(tutorialId);
        String trimmedName = tutorialId.trim();
        if (!TutorialId.isValidTutorialId(trimmedName)) {
            throw new ParseException(TutorialId.MESSAGE_CONSTRAINTS);
        }
        return new TutorialId(trimmedName);
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
     * Parses a {@code String tabs} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tabs} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
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

    // New parsing methods for add_tutorial command

}
