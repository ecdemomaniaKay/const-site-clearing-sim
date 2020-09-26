import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    @Test
    void readFile() {
        FileIO fileIO = new FileIO();
        String dir = "testFiles\\testSite.txt";
        String imported = fileIO.readFile(dir);
        String site = "ootooooooo\noooooooToo\n";
        assertEquals(imported, site);
    }
}