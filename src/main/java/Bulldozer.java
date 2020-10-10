/**
 * Class for bulldozer which moves in a straight line and changes its direction while stationary.
 */

public class Bulldozer {
    // All possible directions the bulldozer can face in clockwise order.
    private final char[] clockwise = new char[]{'E', 'S', 'W', 'N'};

    // the coordinate of the bulldozer's position
    private final int[] position;

    // amount of damage suffered
    private int damage;

    // index of the element in CLOCKWISE that represents the direction the bulldozer is facing
    private int dirIndex;

    // fuel consumption
    private int fuelUsage;


    Bulldozer() {
        position = new int[]{-1, 0}; // initial position at Northern edge of the site, immediately to the West of the site
        damage = 0;
        dirIndex = 0; // initially facing East
        fuelUsage = 0;
    }

    /**
     * Move the bulldozer forward and clear land. Depending on the terrain the amount of fuel consumed
     * is different as well as whether the bulldozer will be scratched.
     *
     * @param route The terrain, i.e. sequence of squares, that the bulldozer will advance through.
     * @return A sequence of cleared squares.
     */
    public String advance(String route) {
        if (route.isEmpty()) {
            return route;
        }

        final StringBuilder stringBuilder = new StringBuilder();
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
            stringBuilder.append("c");
        }

        final int distance = route.length();
        switch (clockwise[dirIndex]) {
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

        return stringBuilder.toString();
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
                    dirIndex = clockwise.length - 1;
                } else {
                    dirIndex -= 1;
                }
                break;
            case 'R':
                if (dirIndex == clockwise.length - 1) {
                    dirIndex = 0;
                } else {
                    dirIndex += 1;
                }
                break;
        }
    }

    public char[] getClockwise() {
        return clockwise.clone();
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
        return clockwise[dirIndex];
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
        return position.clone();
    }
}
