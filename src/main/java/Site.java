import java.util.HashMap;

public class Site {
    private String siteMap;

    Site(String dir) {
        siteMap = new FileIO().readFile(dir);
    }

    public String getSquare(int[] position) {
        String[] rows = siteMap.split("\n");
        return String.valueOf(rows[position[1]].charAt(position[0]));
    }

    public HashMap passed(int[] start, int[] end) {
        if (start[0] == end[0] && start[1] == end[1]) {
            return null; //todo: fix this
        } else {
            HashMap<String, Integer> passed = new HashMap<String, Integer>();

            int horizontalDiff = end[0] - start[0];
            int verticalDiff = end[1] - start[1];

            for (int i = 0; i < Math.abs(horizontalDiff) - 2; i++) {
                // add count of territory
            }
            // loop for vertical

            return passed;
        }
    }
}
