import java.util.zip.DataFormatException;

/**
 * Class for handling the site information including terrain, length and width.
 */

public class Site {
    // Stores the terrain type of each square. Row extends from north to south, and
    // row elements, i.e. columns of the site, from west to east. Hence [0,0] is
    // the northwest corner of the site.
    private final String[] siteRows;

    Site(String map) throws DataFormatException {
        siteRows = new FileIO().readFile(map).split("\n");
        mapValidation();
    }

    /**
     * Check whether the bulldozer will run across the boundary.
     *
     * @param position    The coordinates of the current bulldozer's position.
     * @param orientation The direction the bulldozer is currently facing.
     * @param distance    The distance the bulldozer wishes to advance.
     * @return 0 for the bulldozer will remain within the site, 1 for it will cross the boundary,
     * 2 for it is on the boundary facing away from the site, and 3 when the distance is 0.
     */
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

    /**
     * Check whether the bulldozer will run into a protected tree.
     *
     * @param route The route that the bulldozer plans to take.
     * @return A sequence of squares, representing the terrain, that the bulldozer may advance through,
     * which does not include a protected tree and the squares behind it.
     */
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

    /**
     * Get the sequence of squares, which represents the terrain that the the bulldozer will advance through.
     * If the parameters given will result in the bulldozer going offsite, the sequence of squares that
     * it will pass through before crossing the site boundary will be returned.
     *
     * @param position    The bulldozer's current position.
     * @param orientation The direction the bulldozer is currently facing.
     * @param distance    The distance the bulldozer wishes to advance.
     * @return A sequence of squares, representing the terrain, which is trimmed if needed to ensure the
     * bulldozer only operates within the site's boundaries.
     */
    public String getRoute(int[] position, char orientation, int distance) {
        // check whether the intended advance distance will exceed the site boundary
        int outOfBounds = outOfBounds(position, orientation, distance);

        if (outOfBounds == 3 || outOfBounds == 2) {
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

    /**
     * Count the number of uncleared squares.
     *
     * @return The number of uncleared squares.
     */
    public int countUncleared() {
        int notCleared = 0;
        for (String siteRow : siteRows) {
            notCleared += siteRow.replace("c", "").replace("T", "").length();
        }
        return notCleared;
    }

    /**
     * Prints the site map.
     */
    public void display() {
        for (String siteRow : siteRows) {
            System.out.println(siteRow.replaceAll(".(?!$)", "$0 "));
        }
        System.out.println();
    }

    private boolean siteRowsAreEqualInLength() {
        final int length = siteRows[0].length();
        for (int i = 1; i < siteRows.length; i++) {
            if (siteRows[i].length() != length) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the route for North-bound or South-bound advance plans.
     *
     * @param position The bulldozer's current position.
     * @param distance The distance the bulldozer wishes to advance.
     * @param sign     The direction the bulldozer wishes to advance. 1 for South and -1 for North.
     * @return The sequence of squares for the advance plan.
     */
    private String getColumn(int[] position, int distance, int sign) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < distance + 1; i++) {
            stringBuilder.append(siteRows[position[1] + i * sign].charAt(position[0]));
        }
        return stringBuilder.toString();
    }

    /**
     * Update the site map after advancing through the land.
     *
     * @param position    The bulldozer's current position.
     * @param orientation The direction the bulldozer is currently facing.
     * @param seq         The sequence of squares that will be used to update the map.
     */
    public void setRowCol(int[] position, char orientation, String seq) {
        if (seq.isEmpty()) {
            return;
        }

        switch (orientation) {
            case 'E':
                // if the bulldozer is at the starting position [-1, 0]
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

    /**
     * Checks whether the site map provided is valid.
     *
     * @throws DataFormatException If map contains unequal row lengths or the Northwestern entrance is blocked by a
     *                             protected tree.
     */
    private void mapValidation() throws DataFormatException {
        if (!siteRowsAreEqualInLength()) {
            throw new DataFormatException("DataFormatException: Invalid site. Contains unequal row lengths.");
        } else if (siteRows[0].charAt(0) == 'T') {
            throw new DataFormatException("DataFormatException: Invalid site. Northwestern entrance to site is blocked by a protected tree.");
        }
    }
}
