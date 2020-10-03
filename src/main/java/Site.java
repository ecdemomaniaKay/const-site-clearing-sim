import java.util.HashMap;

public class Site {
    private String[] siteRows;

    Site(String map) {
        siteRows = new FileIO().readFile(map).split("\n");
    }

    public int outOfBounds(int[] position, char orientation, int distance) {
        if (distance == 0) {
            return 3;
        }

        switch (orientation) {
            case 'E':
                if (position[0] == siteRows[0].length() - 1) {
                    return 2;
                } else {
                    return position[0] + distance > siteRows[0].length() ? 1 : 0;
                }
            case 'W':
                if (position[0] <= 0) {
                    return 2;
                } else {
                    return distance > position[0] ? 1 : 0;
                }
            case 'N':
                if (position[1] <= 0) {
                    return 2;
                } else {
                    return position[1] + distance > siteRows.length ? 1 : 0;
                }
            case 'S':
                if (position[1] == siteRows.length - 1 || position[0] == -1) {
                    return 2;
                } else {
                    return distance > position[1] ? 1 : 0;
                }
            default:
                return 0;
        }
    }

    public void display() {
        for (int i = 0; i < siteRows.length; i++) {
            System.out.println(siteRows[i].replaceAll(".(?!$)", "$0 "));
        }
    }

    public String getRoute(int[] position, char orientation, int distance) {
        int outOfBounds = outOfBounds(position, orientation, distance);
        if (outOfBounds == 2 || outOfBounds == 3) {
            return "";
        }

        switch (orientation) {
            case 'E':
                if (outOfBounds == 1) {
                    return siteRows[position[1]].substring(position[0] + 1, siteRows[0].length());
                } else {
                    return siteRows[position[1]].substring(position[0] + 1, position[0] + 1 + distance);
                }
            case 'W':
                if (outOfBounds == 1) {
                    return siteRows[position[1]].substring(0, position[0]);
                } else {
                    return siteRows[position[1]].substring(position[0] - distance, position[0]);
                }
            case 'N':
                if (outOfBounds == 1) {
                    return getColumn(position, position[1] * -1);
                } else {
                    return getColumn(position, distance * -1);
                }
            case 'S':
                if (outOfBounds == 1) {
                    return getColumn(position, siteRows.length - position[1]);
                } else {
                    return getColumn(position, distance);
                }
            default:
                return "";
        }
        // todo: throw error for T
    }

    public char getSquare(int[] position) throws ArrayIndexOutOfBoundsException {
        return siteRows[position[1]].charAt(position[0]);
    }

    public void setSquare(int[] position, char square) {
        StringBuilder row = new StringBuilder(siteRows[position[1]]);
        row.setCharAt(position[0], square);
        siteRows[position[1]] = row.toString();
    }

    private String getColumn(int[] start, int signedDistance) {
        int sign = Integer.signum(signedDistance);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i < Math.abs(signedDistance); i++) {
            stringBuilder.append(siteRows[start[1] + i * sign].charAt(start[0]));
        }

        return stringBuilder.toString();
    }
}
