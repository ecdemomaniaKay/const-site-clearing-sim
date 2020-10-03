import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest {
    static final String MAP = "testFiles\\testSite.txt";

    @Test
    void outOfBound() {
        Site site = new Site(MAP);
        Bulldozer bulldozer = new Bulldozer();

        assertEquals(3, site.outOfBounds(bulldozer.getPosition(), bulldozer.getOrientation(), 0));

        // 0: within bounds, 1: out of bounds, 2: on the boundary facing away from the site
        int[][] expected = new int[][]{{0, 2, 2, 2}, {1, 1, 2, 2}, {2, 1, 1, 2}, {2, 1, 1, 2}, {2, 1, 1, 2}, {0, 0, 0, 0}};
        ArrayList<String> route = new ArrayList<String>();
        route.add("t"); // move to [0,0]
        route.add("otooooooo"); // move to northeast edge
        route.add("oooo"); // move to southeast edge
        route.add("oootrrrrr"); // move to southwest edge
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
    void checkForProtectedTree() {
        Site site = new Site(MAP);
        Bulldozer bulldozer = new Bulldozer();
        bulldozer.advance(site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 2));
        bulldozer.turn('R');
        bulldozer.advance(site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 2));
        bulldozer.turn('L');
        String route = site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 8);
        String checked = site.checkForProtectedTree(route);
        assertEquals("rooooT", checked);
    }

    @Test
    void getRoute() {
        Site site = new Site(MAP);
        Bulldozer bulldozer = new Bulldozer();
        String route = site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 6);
        assertEquals("totooo", route);
    }

    @Test
    void summary() {
        Site site = new Site(MAP);
        int unClearedCount = site.summary();
        assertEquals(50, unClearedCount);

        Bulldozer bulldozer = new Bulldozer();
        bulldozer.advance(site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), 5));
        // dump cleared route back to map
        unClearedCount = site.summary();

    }

    @Test
    void getSquare() {
        Site site = new Site(MAP);
        char square = site.getSquare(new int[]{7, 1});
        assertEquals('T', square);
    }

    @Test
    void setSquare() {
        Site site = new Site(MAP);
        int[] pos1 = new int[]{0, 0};
        site.setSquare(pos1, 'o');
        assertEquals(site.getSquare(pos1), 'o');
    }

    @Test
    void setRowCol() {
        Site site = new Site(MAP);
        Bulldozer bulldozer = new Bulldozer();

        int loop = 4;
        int length = 10;
        int width = 5;
        int[] distance = new int[loop];
        ArrayList<String> expected = new ArrayList<>();
        int[][] start = new int[loop][2];
        char[] orientationActual = new char[loop];
        int[] squares = new int[loop];

        distance[0] = 5;
        expected.add("cccccooooo");
        start[0] = new int[]{-1, 0};
        orientationActual[0] = 'E';
        squares[0] = length;

        distance[1] = 3;
        expected.add("cccr");
        start[1] = new int[]{start[0][0] + distance[0], 0};
        orientationActual[1] = 'S';
        squares[1] = width - 1 - start[1][1];

        distance[2] = 2;
        expected.add("ccrr");
        start[2] = new int[]{start[1][0], start[1][1] + distance[1]};
        orientationActual[2] = 'W';
        squares[2] = start[2][0];

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