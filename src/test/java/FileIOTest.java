import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    @Test
    void convertContentIntoString_WhenInvokeReadFile() {
        FileIO fileIO = new FileIO();
        String dir = "testFiles\\testSite.txt";
        String imported = fileIO.readFile(dir);
        String site = "totooooooo\n" +
                "oooooooToo\n" +
                "rrrooooToo\n" +
                "rrrroooooo\n" +
                "rrrrrtoooo\n";
        assertEquals(imported, site);
    }
}