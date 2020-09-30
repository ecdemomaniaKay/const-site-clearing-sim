import java.util.HashMap;

public class Site {
    private String[] siteRows;

    Site(String map) {
        siteRows = new FileIO().readFile(map).split("\n");
    }

    public void clear(int[] position) {
        char square = getSquare(position);
        if (square == 'T') {
            // todo: end sim
            System.out.println("Simulation terminated.");
        } else {
            setSquare(position, 'c'); // c for cleared
        }
    }

    public char getSquare(int[] position) throws ArrayIndexOutOfBoundsException {
        return siteRows[position[1]].charAt(position[0]);

    }

    public HashMap<String, Integer> passed(int[] start, int[] end) {
        if (start[0] == end[0] && start[1] == end[1]) {
            return null; //todo: fix this
        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("o", 0);
            map.put("t", 0);
            map.put("T", 0);
            map.put("r", 0);
            map.put("error", 0);

            int horizontalDiff = end[0] - start[0];
            int signH = Integer.signum(horizontalDiff);

            int verticalDiff = end[1] - start[1];
            int signV = Integer.signum(verticalDiff);

            for (int i = 1; i <= Math.abs(horizontalDiff) - 1; i++) {
                int pos = start[0] + i * signH;
                if (pos >= siteRows[0].length() || pos < 0) {
                    map.put("error", 1); // out of site boundary
                    return map;
                }
                String key = String.valueOf(getSquare(new int[]{start[0] + i * signH, start[1]}));
                map.put(key, map.get(key) + 1);
                if (key.equals("T")) {
                    map.put("error", 2); // attempt to remove protected tree
                    return map;
                }
            }
            for (int i = 1; i <= Math.abs(verticalDiff) - 1; i++) {
                int pos = start[1] + i * signV;
                if (pos >= siteRows.length || pos < 0) {
                    map.put("error", 1); // out of site boundary
                    return map;
                }
                String key = String.valueOf(getSquare(new int[]{start[0], start[1] + i * signV}));
                map.put(key, map.get(key) + 1);
                if (key.equals("T")) {
                    map.put("error", 2); // attempt to remove protected tree
                    return map;
                }
            }

            if (end[0] >= siteRows[0].length() || end[0] < 0 || end[1] >= siteRows.length || end[1] < 0) {
                map.put("error", 1); // out of site boundary
            }
            return map;
        }
    }

    public void display() {
        for (int i = 0; i < siteRows.length; i++) {
            System.out.println(siteRows[i].replaceAll(".(?!$)", "$0 "));
        }
    }

    public void setSquare(int[] position, char square) {
        StringBuilder row = new StringBuilder(siteRows[position[1]]);
        row.setCharAt(position[0], square);
        siteRows[position[1]] = row.toString();
    }
}
