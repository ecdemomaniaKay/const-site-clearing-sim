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
                System.out.println("orientation:" + bulldozer.getOrientation() + ", position: [" + bulldozer.getPosition()[0] + ", " + bulldozer.getPosition()[1] + "]");
                System.out.println("i: " + i + ", j: " + j);
                assertEquals(expected[i][j], outOfBound);
                bulldozer.turn('R');
            }
            if (i > 1) {
                bulldozer.turn('R');
            }
            bulldozer.advance(route.get(i));
        }
        System.out.println("orientation:" + bulldozer.getOrientation() + ", position: [" + bulldozer.getPosition()[0] + ", " + bulldozer.getPosition()[1] + "]");

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
    void getRoute() {
        Site site = new Site(MAP);
        Bulldozer bulldozer = new Bulldozer();
        int[] position = bulldozer.getPosition();
        char orientation = bulldozer.getOrientation();
        int distance = 6;
        String route = site.getRoute(position, orientation, distance);
        assertEquals("totooo", route);
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
}