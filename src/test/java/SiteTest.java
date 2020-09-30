import org.junit.jupiter.api.Test;

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

    @Test
    void passed() {
        Site site = new Site(MAP);
        int[] advance = new int[]{8, 4, 5, 3};
        char[] turn = new char[]{'S', 'R', 'R', 'R'};
        int[] o = new int[]{5, 1, 1, 0};
        int[] t = new int[]{2, 0, 1, 0};
        int[] bigT = new int[]{0, 2, 0, 0};
        int[] r = new int[]{0, 0, 2, 2};

        Bulldozer bulldozer = new Bulldozer();

        for (int i = 0; i < advance.length; i++) {
            int[] start = bulldozer.getPosition();
            bulldozer.turn(turn[i]);
            bulldozer.advance(advance[i]);
            int[] end = bulldozer.getPosition();

            HashMap<String, Integer> map = new HashMap<>();
            map.put("o", o[i]);
            map.put("t", t[i]);
            map.put("T", bigT[i]);
            map.put("r", r[i]);
            assertTrue(map.equals(site.passed(start, end)));
        }
    }

    @Test
    void clear() {
        int[][] positions = new int[][]{new int[]{0, 0}, new int[]{7, 1}};
        char[] expected = new char[]{'o', 'T'};
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