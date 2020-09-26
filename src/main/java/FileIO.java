import java.io.*;
import java.util.Scanner;

public class FileIO {
    public String readFile(String dir) {
        StringBuffer fileContents = new StringBuffer();
        try {
            FileReader inputFile = new FileReader(dir);
            try {
                Scanner parser = new Scanner(inputFile);
                while (parser.hasNextLine()) {
                    fileContents.append(parser.nextLine() + "\n");
                }
            } finally {
                inputFile.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(dir + " not found");
        } catch (IOException e) {
            System.out.println("Unexpected I/O error");
        }

        return fileContents.toString();
    }
}
