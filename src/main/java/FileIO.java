import java.io.*;
import java.util.Scanner;

/**
 * Class that reads files.
 */
public class FileIO {
    /**
     * Read a file.
     *
     * @param dir The filepath.
     * @return The contents of the file.
     */
    public String readFile(String dir) {
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            try (FileReader inputFile = new FileReader(dir)) {
                final Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine()) {
                    stringBuilder.append(parser.nextLine()).append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file. File " + dir + " not found.");
        } catch (IOException e) {
            System.out.println("Unexpected I/O error");
        }

        return stringBuilder.toString();
    }
}
