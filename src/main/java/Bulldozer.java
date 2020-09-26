public class Bulldozer {
    private int[] position;
    private int dirIndex;
    public static final char[] CLOCKWISE = new char[]{'E', 'S', 'W', 'N'};

    Bulldozer() {
        position = new int[]{-1, 0};
        dirIndex = 0;
    }

    public char getDirIndex() {
        return CLOCKWISE[dirIndex];
    }

    public int[] getPosition() {
        return new int[]{position[0], position[1]};
    }

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
