import java.util.HashMap;

public class Site {
    private String[] siteRows;

    Site(String dir) {
        siteRows = new FileIO().readFile(dir).split("\n");
    }

    public char getSquare(int[] position) throws ArrayIndexOutOfBoundsException {
        return siteRows[position[1]].charAt(position[0]);

    }

    public HashMap passed(int[] start, int[] end) {
        if (start[0] == end[0] && start[1] == end[1]) {
            return null; //todo: fix this
        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("o", 0);
            map.put("t", 0);
            map.put("T", 0);
            map.put("r", 0);

            int horizontalDiff = end[0] - start[0];
            int signH = Integer.signum(horizontalDiff);

            int verticalDiff = end[1] - start[1];
            int signV = Integer.signum(verticalDiff);

            for (int i = 1; i <= Math.abs(horizontalDiff) - 1; i++) {
                String key = String.valueOf(getSquare(new int[]{start[0] + i * signH, start[1]}));
                map.put(key, map.get(key) + 1);
            }
            for (int i = 1; i <= Math.abs(verticalDiff) - 1; i++) {
                String key = String.valueOf(getSquare(new int[]{start[0], start[1] + i * signV}));
                map.put(key, map.get(key) + 1);
            }

            return map;
        }
    }

    public void clear(int[] position) {
        char square = getSquare(position);
        if (square == 'T') {
            // todo: end sim
            System.out.println("Simulation terminated.");
        } else if (square != 'o') {
            setSquare(position, 'o');
        }
    }

    public void setSquare(int[] position, char square) {
        StringBuilder row = new StringBuilder(siteRows[position[1]]);
        row.setCharAt(position[0], square);
        siteRows[position[1]] = row.toString();
    }
}
