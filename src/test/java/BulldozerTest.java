import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulldozerTest {

    @Test
    void getDirIndex() {
        Bulldozer bulldozer = new Bulldozer();
        assertEquals('E', bulldozer.getOrientation());
    }

    @Test
    void getPosition() {
        Bulldozer bulldozer = new Bulldozer();
        assertArrayEquals(new int[]{-1, 0}, bulldozer.getPosition());
    }

    @Test
    void advanceGetDamageGetFuelUsage() {
        Bulldozer bulldozer = new Bulldozer();
        String route = "toorrot";
        String clearedRoute = bulldozer.advance(route);
        assertEquals("ccccccc", clearedRoute);
        assertEquals(1, bulldozer.getDamage());
        assertEquals(11, bulldozer.getFuelUsage());
        assertArrayEquals(new int[]{6, 0}, bulldozer.getPosition());
    }

    @Test
    void turn() {
        Bulldozer bulldozer = new Bulldozer();
        final char[] CLOCKWISE = bulldozer.CLOCKWISE;

        for (int i = 1; i <= CLOCKWISE.length; i++) {
            bulldozer.turn('R');
            assertEquals(CLOCKWISE[i % CLOCKWISE.length], bulldozer.getOrientation());
        }
        for (int i = CLOCKWISE.length - 1; i >= 0; i--) {
            bulldozer.turn('L');
            assertEquals(CLOCKWISE[i % CLOCKWISE.length], bulldozer.getOrientation());
        }
    }
}