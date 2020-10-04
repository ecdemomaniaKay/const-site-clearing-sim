import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {
    static final String MAP = "testFiles\\testSite.txt";
    Site site;
    Bulldozer bulldozer;

    @BeforeEach
    public void init() {
        site = new Site(MAP);
        bulldozer = new Bulldozer();
    }

    @Test
    void outOfBound() {
        // 0: within bounds, 1: out of bounds, 2: on the boundary facing away from the site
        assertEquals(3, site.outOfBounds(bulldozer.getPosition(), bulldozer.getOrientation(), 0));

        int[][] expected = new int[6][4];
        ArrayList<String> route = new ArrayList<>();
        expected[0] = new int[]{0, 2, 2, 2};
        route.add("t"); // move to [0,0]
        expected[1] = new int[]{1, 1, 2, 2};
        route.add("otooooooo"); // move to northeast edge
        expected[2] = new int[]{2, 1, 1, 2};
        route.add("oooo"); // move to southeast edge
        expected[3] = new int[]{2, 1, 1, 2};
        route.add("oootrrrrr"); // move to southwest edge
        expected[4] = new int[]{2, 1, 1, 2};
        route.add("rrot"); // move to northwest edge [0,0]

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 4; j++) {
                int distance = i == 0 ? 1 : 100;
                int outOfBound = site.outOfBounds(bulldozer.getPosition(), bulldozer.getOrientation(), distance);
                assertEquals(expected[i][j], outOfBound);
                bulldozer.turn('R');
            }
            if (i > 1) {
                bulldozer.turn('R');
            }
            bulldozer.advance(route.get(i));
        }

        // move to [1,1]
        bulldozer.turn('R');
        bulldozer.advance("t");
        bulldozer.turn('R');
        bulldozer.advance("o");
        expected[5] = new int[]{0, 0, 0, 0};

        for (int j = 0; j < 4; j++) {
            int outOfBound = site.outOfBounds(bulldozer.getPosition(), bulldozer.getOrientation(), 1);
            assertEquals(expected[5][j], outOfBound);
            bulldozer.turn('R');
        }
    }

    // Reference:
    // Jonathan Cook https://www.baeldung.com/java-testing-system-out-println
    @Test
    void display() {
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
    void checkForProtectedTree() {
        bulldozer.advance(site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 2));
        bulldozer.turn('R');
        bulldozer.advance(site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 2));
        bulldozer.turn('L');
        String route = site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 8);
        String checked = site.checkForProtectedTree(route);
        assertEquals("roooo", checked);
    }

    @Test
    void getRoute() {
        String route = site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 6);
        assertEquals("totooo", route);
    }

    @Test
    void countUncleared() {
        assertEquals(48, site.countUncleared());

        int[] position = bulldozer.getPosition();
        char orientation = bulldozer.getOrientation();
        String clearedSquares =
                bulldozer.advance(site.getRoute(position, orientation, 1));
        site.setRowCol(position, orientation, clearedSquares);
        assertEquals(47, site.countUncleared());
    }

    @Test
    void setRowCol() {
        int loop = 4;
        int length = 10;
        int width = 5;
        int[] distance = new int[loop];
        ArrayList<String> expected = new ArrayList<>();
        int[][] start = new int[loop][2];
        char[] orientationActual = new char[loop];
        int[] squares = new int[loop];

        // case 1: advance east 5 squares
        distance[0] = 5;
        expected.add("cccccooooo");
        start[0] = new int[]{-1, 0};
        orientationActual[0] = 'E';
        squares[0] = length;

        // case 2: continue south 3 squares
        distance[1] = 3;
        expected.add("cccr");
        start[1] = new int[]{start[0][0] + distance[0], 0};
        orientationActual[1] = 'S';
        squares[1] = width - 1 - start[1][1];

        // case 3: continue west 2 squares
        distance[2] = 2;
        expected.add("ccrr");
        start[2] = new int[]{start[1][0], start[1][1] + distance[1]};
        orientationActual[2] = 'W';
        squares[2] = start[2][0];

        // case 4: continue north 2 squares
        distance[3] = 2;
        expected.add("ccc");
        start[3] = new int[]{start[2][0] - distance[2], start[2][1]};
        orientationActual[3] = 'N';
        squares[3] = start[3][1];

        for (int i = 0; i < loop; i++) {
            int[] position = bulldozer.getPosition();
            char orientation = bulldozer.getOrientation();
            String clearedSquares =
                    bulldozer.advance(site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), distance[i]));
            site.setRowCol(position, orientation, clearedSquares);
            assertEquals(expected.get(i), site.getRoute(start[i], orientationActual[i], squares[i]));
            bulldozer.turn('R');
        }
    }
}