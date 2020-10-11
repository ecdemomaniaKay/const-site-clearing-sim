import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;


class SiteTest {
    static final String MAP = "testFiles\\testSite.txt";
    Site site;
    Bulldozer bulldozer;

    @BeforeEach
    public void init() {
        try {
            site = new Site(MAP);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        bulldozer = new Bulldozer();
    }

    @Test
    void givenAdvancePlanWillNotCrossSiteBoundary_return0_whenInvokeOutOfBound() {
        int[] position = new int[]{0, 0};
        char orientation = 'E';
        assertEquals(0, site.outOfBounds(position, orientation, 5));
    }

    @Test
    void givenAdvancePlanWillCrossSiteBoundary_return1_whenInvokeOutOfBound() {
        int[] position = new int[]{1, 1};
        char[] orientation = new char[]{'E', 'S', 'W', 'N'};
        for (int i = 0; i < position.length; i++) {
            assertEquals(1, site.outOfBounds(position, orientation[i], 10));
        }
    }

    @Test
    void givenOnBoundaryAndDistanceIsGreaterThan1_return2_whenInvokeOutOfBound() {
        int[][] position = new int[][]{new int[]{0, 0}, new int[]{10, 10}};
        char[] orientation = new char[]{'N', 'W', 'E', 'S'};

        int i = 0;
        int j = 0;
        while (i < position.length) {
            assertEquals(2, site.outOfBounds(position[j], orientation[i], 1));
            i++;
            j = i / 2;
        }
    }

    @Test
    void givenDistance0_return3_whenInvokeOutOfBound() {
        int[] position = new int[]{0, 0};
        char orientation = 'N';
        assertEquals(3, site.outOfBounds(position, orientation, 0));
    }

    @Test
    void givenRouteIncludesProtectedTree_excludeProtectedTreeAndBeyondFromRoute_whenInvokeCheckForProtectedTree() {
        String checked = site.checkForProtectedTree("rooooToo");
        assertEquals("roooo", checked);
    }

    @Test
    void givenAdvancePlan_returnTheSquaresThatAreInsideSiteBoundaries_whenInvokeGetRoute() {
        String route = site.getRoute(new int[]{-1, 0}, 'E', 6);
        assertEquals("totooo", route);
    }

    @Test
    void givenInitializedSite_countAmountOfUnclearedSquares_whenInvokeCountUncleared() {
        assertEquals(48, site.countUncleared());
        site.setRowCol(new int[]{-1, 0}, 'E', "c");
        assertEquals(47, site.countUncleared());
    }

    // Reference:
    // Jonathan Cook https://www.baeldung.com/java-testing-system-out-println
    @Test
    void givenInitializedSite_printSiteTerrainMapInConsole_whenInvokeDisplay() throws Exception {
        String map = "t o t o o o o o o o\n" +
                "o o o o o o o T o o\n" +
                "r r r o o o o T o o\n" +
                "r r r r o o o o o o\n" +
                "r r r r r t o o o o\n" +
                "\n";
        String text = tapSystemOutNormalized(() -> site.display());
        assertEquals(map, text);
    }

    @Test
    void givenClearedRoute_updateSiteMap_whenInvokeSetRowCol() throws Exception {
        int[][] position = new int[][]{new int[]{-1, 0}, {4, 0}, {4, 3}, {1, 3}};
        char[] orientation = new char[]{'E', 'S', 'W', 'N'};
        String[] clearedSquares = new String[]{"ccccc", "ccc", "ccc", "ccc"};
        String[] expected = new String[]{
                "c c c c c o o o o o\n" +
                        "o o o o o o o T o o\n" +
                        "r r r o o o o T o o\n" +
                        "r r r r o o o o o o\n" +
                        "r r r r r t o o o o\n" +
                        "\n",
                "c c c c c o o o o o\n" +
                        "o o o o c o o T o o\n" +
                        "r r r o c o o T o o\n" +
                        "r r r r c o o o o o\n" +
                        "r r r r r t o o o o\n" +
                        "\n",
                "c c c c c o o o o o\n" +
                        "o o o o c o o T o o\n" +
                        "r r r o c o o T o o\n" +
                        "r c c c c o o o o o\n" +
                        "r r r r r t o o o o\n" +
                        "\n",
                "c c c c c o o o o o\n" +
                        "o c o o c o o T o o\n" +
                        "r c r o c o o T o o\n" +
                        "r c c c c o o o o o\n" +
                        "r r r r r t o o o o\n" +
                        "\n",
        };

        for (int i = 0; i < position.length; i++) {
            site.setRowCol(position[i], orientation[i], clearedSquares[i]);
            String text = tapSystemOutNormalized(() -> site.display());
            assertEquals(expected[i], text);
        }
    }
}