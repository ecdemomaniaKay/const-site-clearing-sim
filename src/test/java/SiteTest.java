import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {
    static final String MAP = "testFiles\\testSite.txt";

    @Test
    void getSquare() {
        Site site = new Site(MAP);
        char square = site.getSquare(new int[]{7, 1});
        assertEquals('T', square);
    }


    // Reference:
    // Jonathan Cook https://www.baeldung.com/java-testing-system-out-println
    @Test
    void print() {
        Site site = new Site(MAP);
        String map = "totooooooo\n" +
                "oooooooToo\n" +
                "rrrooooToo\n" +
                "rrrroooooo\n" +
                "rrrrrtoooo\n";

        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        site.display();

//        assertEquals(map, outputStreamCaptor.toString());
        System.setOut(standardOut);
    }

    @Test
    void passed() {
        Site site = new Site(MAP);
        int[] advance = new int[]{8, 4, 5, 3, 2};
        char[] turn = new char[]{'S', 'R', 'R', 'R', 'S'};
        int[] o = new int[]{5, 0, 1, 0, 0};
        int[] t = new int[]{2, 0, 1, 0, 1};
        int[] bigT = new int[]{0, 1, 0, 0, 0};
        int[] r = new int[]{0, 0, 2, 2, 0};
        int[] e = new int[]{0, 2, 0, 0, 1};

        Bulldozer bulldozer = new Bulldozer();

        for (int i = 0; i < advance.length; i++) {
            int[] start = bulldozer.getPosition();
            bulldozer.turn(turn[i]);
            bulldozer.advance(advance[i]);
            int[] end = bulldozer.getPosition();

            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("o", o[i]);
            hashMap.put("t", t[i]);
            hashMap.put("T", bigT[i]);
            hashMap.put("r", r[i]);
            hashMap.put("error", e[i]);

            HashMap<String, Integer> testHashMap = site.passed(start, end);
            if (testHashMap.containsKey("error")) {
                if (testHashMap.get("error") == 1) {
                    System.out.println("Bulldozer has gone off the site boundary.");
                } else if (testHashMap.get("error") == 2) {
                    System.out.println("Bulldozer has attempted to remove a protected tree.");
                }
            }

            assertEquals(hashMap, testHashMap);

        }
    }

    @Test
    void clear() {
        int[][] positions = new int[][]{new int[]{0, 0}, new int[]{7, 1}};
        char[] expected = new char[]{'c', 'T'};
        Site site = new Site(MAP);
        for (int i = 0; i < positions.length; i++) {
            site.clear(positions[i]);
            assertEquals(site.getSquare(positions[i]), expected[i]);
        }
    }

    @Test
    void setSquare() {
        Site site = new Site(MAP);
        int[] pos1 = new int[]{0, 0};
        site.setSquare(pos1, 'o');
        assertEquals(site.getSquare(pos1), 'o');
    }
}