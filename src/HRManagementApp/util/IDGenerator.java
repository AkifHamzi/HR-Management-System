package HRManagementApp.util;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A utility for generating unique, auto-incrementing IDs.
 */
public class IDGenerator {

    /**
     * Generates the next available ID for a collection of items.
     * It finds the maximum current ID and returns max_id + 1.
     *
     * @param lines       The list of strings from the data file.
     * @param fromString  A function to convert a file line into an object.
     * @param idExtractor A function to extract the ID from an object.
     * @param <T>         The type of the entity (e.g., Employee, Department).
     * @return The next unique ID (long).
     */
    public static <T> long getNextId(List<String> lines, Function<String, T> fromString, Function<T, Long> idExtractor) {
        if (lines == null || lines.isEmpty()) {
            return 1L;
        }

        long maxId = lines.stream()
                .map(fromString)
                .mapToLong(idExtractor::apply)
                .max()
                .orElse(0L);

        return maxId + 1;
    }
}