/**
 * Class for bulldozer which moves in a straight line and changes its direction while stationary.
 */

public class Bulldozer {
    final private int[] position;
    private int dirIndex;
    public static final char[] CLOCKWISE = new char[]{'E', 'S', 'W', 'N'}; // possible directions in clockwise order

    Bulldozer() {
        position = new int[]{-1, 0}; // initial position at Northern edge of the site, immediately to the West of the site, facing East
        dirIndex = 0;
    }

    /**
     * Get the <code>CLOCKWISE</code> index of the direction the bulldozer is facing.
     *
     * @return The index of the element in <code>CLOCKWISE</code> that represents the direction the bulldozer is facing.
     */
    public char getDirIndex() {
        return CLOCKWISE[dirIndex];
    }

    /**
     * Get the position of the bulldozer.
     *
     * @return int[] The position of the bulldozer.
     */
    public int[] getPosition() {
        return new int[]{position[0], position[1]};
    }

    /**
     * Move the bulldozer forward x many squares.
     *
     * @param distance The number of squares the bulldozer should advance.
     */
    public void advance(int distance) {
        switch (CLOCKWISE[dirIndex]) {
            case 'E':
                position[0] += distance;
                break;
            case 'W':
                position[0] -= distance;
                break;
            case 'S':
                position[1] += distance;
                break;
            case 'N':
                position[1] -= distance;
                break;
            default:
                // todo: add default action
        }
    }

    /**
     * Turn the bulldozer to face another direction. 1 step clockwise or 1 step counter-clockwise.
     *
     * @param orientation L for 1 step counter-clockwise, R for 1 step clockwise.
     */
    public void turn(char orientation) {
        switch (orientation) {
            case 'L':
                if (dirIndex == 0) {
                    dirIndex = CLOCKWISE.length - 1;
                } else {
                    dirIndex -= 1;
                }
                break;
            case 'R':
                if (dirIndex == CLOCKWISE.length - 1) {
                    dirIndex = 0;
                } else {
                    dirIndex += 1;
                }
                break;
            default:
                // todo: add default action
        }
    }
}
