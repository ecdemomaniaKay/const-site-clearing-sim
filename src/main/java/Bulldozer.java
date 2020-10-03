/**
 * Class for bulldozer which moves in a straight line and changes its direction while stationary.
 */

public class Bulldozer {
    public static final char[] CLOCKWISE = new char[]{'E', 'S', 'W', 'N'}; // possible directions in clockwise order
    private int dirIndex;
    final private int[] position;
    int fuelUsage;
    int damage;

    Bulldozer() {
        dirIndex = 0; // initially facing East
        position = new int[]{-1, 0}; // initial position at Northern edge of the site, immediately to the West of the site, facing East
        fuelUsage = 0;
        damage = 0;
    }

    /**
     * Get the amount of scratches on the bulldozer.
     *
     * @return The amount of scratches on the bulldozer.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Get the <code>CLOCKWISE</code> index of the direction the bulldozer is facing.
     *
     * @return The index of the element in <code>CLOCKWISE</code> that represents the direction the bulldozer is facing.
     */
    public char getOrientation() {
        return CLOCKWISE[dirIndex];
    }

    /**
     * Get the amount of fuel used.
     *
     * @return The amount of fuel used.
     */
    public int getFuelUsage() {
        return fuelUsage;
    }

    /**
     * Get the position of the bulldozer.
     *
     * @return The position of the bulldozer.
     */
    public int[] getPosition() {
        return new int[]{position[0], position[1]};
    }

    /**
     * Move the bulldozer forward and clear land at the same time.
     *
     * @param route The terrain, i.e. sequence of squares, that the bulldozer will advance through.
     * @return A sequence of cleared squares.
     */
    public String advance(String route) {
        StringBuffer clearedRoute = new StringBuffer();
        for (int i = 0; i < route.length(); i++) {
            switch (route.charAt(i)) {
                case 'c':
                case 'o':
                    fuelUsage += 1;
                    break;
                case 'r':
                    fuelUsage += 2;
                    break;
                case 't':
                    fuelUsage += 2;
                    if (i != route.length() - 1) {
                        damage += 1;
                    }
                    break;
            }
            clearedRoute.append("c");
        }

        int distance = route.length();
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
        }

        return clearedRoute.toString();
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
