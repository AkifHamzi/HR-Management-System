package HRManagementApp.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for file operations.
 * Handles reading from and writing to text files.
 */
public class FileUtils {

    /**
     * Reads all lines from a given file.
     * Creates the file and its parent directories if they do not exist.
     *
     * @param filePath The path to the file.
     * @return A list of strings, where each string is a line from the file.
     * @throws IOException If an I/O error occurs.
     */
    public static List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            return Collections.emptyList();
        }
        return Files.readAllLines(path);
    }

    /**
     * Writes a list of lines to a file, overwriting its existing content.
     *
     * @param filePath The path to the file.
     * @param lines    The list of strings to write to the file.
     * @throws IOException If an I/O error occurs.
     */
    public static void writeAllLines(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Checks if a file is new or empty.
     *
     * @param filePath The path to the file.
     * @return true if the file does not exist or has a size of 0.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean isNewFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return !Files.exists(path) || Files.size(path) == 0;
    }
}