import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulldozerTest {

    @Test
    void getDirIndex() {
        Bulldozer bulldozer = new Bulldozer();
        assertEquals('E', bulldozer.getDirIndex());
    }

    @Test
    void getPosition() {
        Bulldozer bulldozer = new Bulldozer();
        assertArrayEquals(new int[]{-1, 0}, bulldozer.getPosition());
    }

    @Test
    void advance() {
        Bulldozer bulldozer = new Bulldozer();
        bulldozer.advance(1);
        assertArrayEquals(new int[]{0, 0}, bulldozer.getPosition());
    }

    @Test
    void turn() {
        Bulldozer bulldozer = new Bulldozer();
        final char[] CLOCKWISE = bulldozer.CLOCKWISE;

        for (int i = 1; i <= CLOCKWISE.length; i++) {
            bulldozer.turn('R');
            assertEquals(CLOCKWISE[i % CLOCKWISE.length], bulldozer.getDirIndex());
        }
        for (int i = CLOCKWISE.length - 1; i >= 0; i--) {
            bulldozer.turn('L');
            assertEquals(CLOCKWISE[i % CLOCKWISE.length], bulldozer.getDirIndex());
        }
    }
}