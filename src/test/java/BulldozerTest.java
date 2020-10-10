import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BulldozerTest {

    Bulldozer bulldozer;
    String route;

    @BeforeEach
    public void init() {
        bulldozer = new Bulldozer();
        route = "toorrot";
    }

    @Test
    void givenInitializedBulldozer_updateBulldozerPosition_whenInvokeAdvance() {
        assertArrayEquals(new int[]{-1, 0}, bulldozer.getPosition());
        bulldozer.advance(route);
        assertArrayEquals(new int[]{6, 0}, bulldozer.getPosition());
    }

    @Test
    void givenRanThroughTree_updateBulldozerDamage_whenInvokeAdvance() {
        assertEquals(0, bulldozer.getDamage());
        bulldozer.advance(route);
        assertEquals(1, bulldozer.getDamage());
    }

    @Test
    void givenAdvanced_increaseBulldozerFuelUsage_whenInvokeAdvance() {
        assertEquals(0, bulldozer.getFuelUsage());
        bulldozer.advance(route);
        assertEquals(11, bulldozer.getFuelUsage());
    }

    @Test
    void givenBulldozerAdvanced_returnClearedRoute_whenInvokeAdvance() {
        String clearedRoute = bulldozer.advance(route);
        assertEquals("ccccccc", clearedRoute);
    }


    @Test
    void givenBulldozerTurned_updateOrientation_whenInvokeTurn() {
        assertEquals('E', bulldozer.getOrientation());

        final char[] CLOCKWISE = bulldozer.getClockwise();

        // turing right/clockwise
        for (int i = 1; i <= CLOCKWISE.length; i++) {
            bulldozer.turn('R');
            assertEquals(CLOCKWISE[i % CLOCKWISE.length], bulldozer.getOrientation());
        }
        // turning left/counter-clockwise
        for (int i = CLOCKWISE.length - 1; i >= 0; i--) {
            bulldozer.turn('L');
            assertEquals(CLOCKWISE[i % CLOCKWISE.length], bulldozer.getOrientation());
        }
    }
}