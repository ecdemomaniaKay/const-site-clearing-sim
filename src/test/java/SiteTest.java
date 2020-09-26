import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {
    static final String dir = "testFiles\\testSite.txt";

    @Test
    void getSquare() {
        Site site = new Site(dir);
        assertEquals("T", site.getSquare(new int[]{7, 1}));
    }

    @Test
    void passThru() {
        FileIO fileIO = new FileIO();
        String dir = "testFiles\\testSite.txt";
        String siteMap = fileIO.readFile(dir);

        Bulldozer bulldozer = new Bulldozer();
        int[] start = bulldozer.getPosition();
        bulldozer.advance(5);
        int[] end = bulldozer.getPosition();

        HashMap<String, Integer> passed = new HashMap<String, Integer>();
        passed.put("o", 4);
        passed.put("t", 1);

        Site site = new Site(dir);
//        assertTrue(passed.equals(site.passed(start, end)));
    }
}