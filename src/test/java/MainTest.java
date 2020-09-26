import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    void importSiteTest() {
        FileIO fileIO = new FileIO();
        String dir = "../../test/testSite.txt";
        String[] importedSite = fileIO.importSite(dir);
        String[] site = {"ootooooooo", "oooooooToo"};
        assertArrayEquals(importedSite, site);
    }
}
