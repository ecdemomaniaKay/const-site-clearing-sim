import java.util.HashMap;

public class Site {
    private String[] siteRows;

    Site(String map) {
        siteRows = new FileIO().readFile(map).split("\n");
    }

    // 0: within bounds, 1: out of bounds, 2: on the boundary facing away from the site
    public int outOfBounds(int[] position, char orientation, int distance) {
        if (distance == 0) {
            return 3;
        }

        switch (orientation) {
            case 'E':
                if (position[0] == siteRows[0].length() - 1) {
                    return 2;
                } else {
                    return position[0] + distance >= siteRows[0].length() ? 1 : 0;
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
                    return position[1] - distance < 0 ? 1 : 0;
                }
            case 'S':
                if (position[1] == siteRows.length - 1 || position[0] == -1) {
                    return 2;
                } else {
                    return distance > siteRows.length - 1 - position[1] ? 1 : 0;
                }
            default:
                return 0;
        }
    }

    public String checkForProtectedTree(String route) {
        if (route.isEmpty()) {
            return route;
        }

        int i = route.indexOf('T');
        if (i == -1) {
            return route;
        } else {
            return route.substring(0, i);
        }
    }

    public String getRoute(int[] position, char orientation, int distance) {
        int outOfBounds = outOfBounds(position, orientation, distance);
        if (outOfBounds == 3) {
            return "";
        } else if (outOfBounds == 2) {
            return "OutOfBoundsError";
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
                    return new StringBuilder(siteRows[position[1]].substring(0, position[0])).reverse().toString();
                } else {
                    return new StringBuilder(siteRows[position[1]].substring(position[0] - distance, position[0])).reverse().toString();
                }
            case 'N':
                if (outOfBounds == 1) {
                    return getColumn(position, position[1], -1);
                } else {
                    return getColumn(position, distance, -1);
                }
            case 'S':
                if (outOfBounds == 1) {
                    return getColumn(position, siteRows.length - 1 - position[1], 1);
                } else {
                    return getColumn(position, distance, 1);
                }
            default:
                return "";
        }
    }

    public int countUncleared() {
        int cleared = 0;
        for (int i = 0; i < siteRows.length; i++) {
            cleared += siteRows[i].length() - siteRows[i].replace("c", "").length();
        }
        return siteRows.length * siteRows[0].length() - cleared;
    }

    public void display() {
        for (int i = 0; i < siteRows.length; i++) {
            System.out.println(siteRows[i].replaceAll(".(?!$)", "$0 "));
        }
    }

    private String getColumn(int[] start, int distance, int sign) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < distance + 1; i++) {
            stringBuilder.append(siteRows[start[1] + i * sign].charAt(start[0]));
        }
        return stringBuilder.toString();
    }

    public char getSquare(int[] position) throws ArrayIndexOutOfBoundsException {
        return siteRows[position[1]].charAt(position[0]);
    }

    public void setSquare(int[] position, char square) {
        StringBuilder row = new StringBuilder(siteRows[position[1]]);
        row.setCharAt(position[0], square);
        siteRows[position[1]] = row.toString();
    }

    public void setRowCol(int[] position, char orientation, String seq) {
        if (seq.isEmpty()) {
            return;
        }

        switch (orientation) {
            case 'E':
                if (position[0] < 0) {
                    siteRows[position[1]] =
                            new StringBuilder(siteRows[position[1]])
                                    .replace(0, seq.length(), seq)
                                    .toString();
                } else {
                    siteRows[position[1]] =
                            new StringBuilder(siteRows[position[1]])
                                    .replace(position[0] + 1, position[0] + seq.length() + 1, seq)
                                    .toString();
                }
                break;
            case 'W':
                siteRows[position[1]] =
                        new StringBuilder(siteRows[position[1]])
                                .replace(
                                        position[0] - seq.length(),
                                        position[0],
                                        new StringBuilder(seq).reverse().toString())
                                .toString();
                break;
            case 'S':
                for (int i = 0; i < seq.length(); i++) {
                    siteRows[position[1] + i + 1] =
                            new StringBuilder(siteRows[position[1] + i + 1])
                                    .replace(position[0], position[0] + 1, seq.substring(i, i + 1))
                                    .toString();
                }
                break;
            case 'N':
                for (int i = 0; i < seq.length(); i++) {
                    siteRows[position[1] - i - 1] =
                            new StringBuilder(siteRows[position[1] - i - 1])
                                    .replace(position[0], position[0] + 1, seq.substring(i, i + 1))
                                    .toString();
                }
                break;
        }
    }

}
