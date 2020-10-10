import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simulator that simulates the coordination between a construction site supervisor and bulldozer operator
 * to clear a site in preparation for building.
 */
public class Simulator {
    // The site object which handles information regarding the site
    private final Site site;

    // The bulldozer object which can clear the land
    private final Bulldozer bulldozer;

    // A list of all commands issued
    private final ArrayList<String> commIssued;

    // The whether the protected tree has been destroyed. 0 for not destroyed, 1 for destroyed.
    private int protectedTreeDestroyed;


    Simulator(String map) {
        site = new Site(map);
        bulldozer = new Bulldozer();
        commIssued = new ArrayList<>();
        protectedTreeDestroyed = 0;
    }

    /**
     * Prints welcome message and print site map.
     */
    public void welcome() {
        System.out.println("\nWelcome to the Aconex site clearing simulator. This is a map of the site:\n");
        site.display();
        System.out.println("The bulldozer is currently located at the Northern edge of the" +
                " site, immediately to the West of the site, and facing East.\n");
    }

    /**
     * Prompts user to issue a command.
     *
     * @return Whether the program should terminate.
     */
    public String addCommand() {
        System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
        return new Scanner(System.in).nextLine().toLowerCase().trim();
    }

    /**
     * Prompts user to issue a command.
     *
     * @return Whether the program should terminate.
     */
    public boolean action(String command) {
        switch (command) {
            case "l":
                bulldozer.turn('L');
                commIssued.add("turn left");
                break;
            case "r":
                bulldozer.turn('R');
                commIssued.add("turn right");
                break;
            case "q":
                commIssued.add("quit");
                terminate("at your request");
                return true;
            default:
                if (command.matches("a\\s+[1-9]\\d*")) {
                    final int distance = Integer.parseInt(command.split("\\s+")[1]);
                    final int[] position = bulldozer.getPosition();
                    final char orientation = bulldozer.getOrientation();

                    final String route = site.getRoute(position, orientation, distance);
                    final String checked = site.checkForProtectedTree(route);
                    final String cleared = bulldozer.advance(checked);
                    site.setRowCol(position, orientation, cleared);

                    // debug
//                    site.display();
//                    System.out.println("pos: [" + bulldozer.getPosition()[0] + ", " + bulldozer.getPosition()[1] + "]");
//                    System.out.println("orientation: " + bulldozer.getOrientation());

                    commIssued.add("advance " + distance);

                    if (checked.length() != route.length()) {
                        protectedTreeDestroyed = 1;
                        terminate("because you attempted to removed a protected tree");
                        return true;
                    } else if (route.length() != distance) {
                        terminate("because you attempted to navigated beyond the boundary of the site");
                        return true;
                    }
                } else if (command.matches("a\\s+0\\d*")) {
                    System.out.println("Please remove leading zero.");
                } else {
                    System.out.println(command + " is an invalid command. Please try again.");
                }
        }
        return false;
    }

    /**
     * Prints termination message and the summary of the simulation session.
     *
     * @param reason The reason of termination.
     */
    private void terminate(String reason) {
        System.out.println();
        System.out.println("The simulation has ended " + reason + ". These are the commands you issued:\n");
        commSummary();
        System.out.println("The costs for this land clearing operation were:\n");
        costSummary();
        System.out.println("Thank you for using the Aconex site clearing simulator.\n");
    }

    /**
     * Print the list of commands that have been issued by the user.
     */
    private void commSummary() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commIssued.size(); i++) {
            if (i != commIssued.size() - 1) {
                stringBuilder.append(commIssued.get(i)).append(", ");
            } else {
                stringBuilder.append(commIssued.get(i));
            }
        }
        System.out.println(stringBuilder.toString() + "\n");
    }

    /**
     * Print the operation cost report, consisting of a list of items, quantity and cost of each item, and
     * the total cost of the operation.
     */
    private void costSummary() {
        final int commUnitCost = 1;
        final int fuelUnitCost = 1;
        final int unclearedUnitCost = 3;
        final int protectedTreeUnitCost = 10;
        final int damageUnitCost = 2;

        // width of the summary table
        final int gap1 = 42;
        final int gap2 = 8;

        final String label1 = "Item";
        final String label2 = "Quantity";
        final String label3 = "Cost";
        final String item1 = "communication overhead";
        final String item2 = "fuel usage";
        final String item3 = "uncleared squares";
        final String item4 = "destruction of protected tree";
        final String item5 = "paint damage to bulldozer";
        final String total = "Total";
        final int commCount =
                commIssued.get(commIssued.size() - 1).equals("quit") ? commIssued.size() - 1 : commIssued.size();
        final int commCost = commCount * commUnitCost;
        final int fuel = bulldozer.getFuelUsage();
        final int fuelCost = bulldozer.getFuelUsage() * fuelUnitCost;
        final int uncleared = site.countUncleared();
        final int unclearedCost = site.countUncleared() * unclearedUnitCost;
        final int treeCost = protectedTreeDestroyed * protectedTreeUnitCost;
        final int damage = bulldozer.getDamage();
        final int damageCost = bulldozer.getDamage() * damageUnitCost;
        final int sum = commCost + fuelCost + unclearedCost + treeCost + damageCost;

        System.out.println(
                label1 + emptySpace(gap1 - label1.length() - label2.length()) +
                        label2 + emptySpace(gap2 - label3.length()) +
                        label3);
        System.out.println(
                item1 + emptySpace(gap1 - item1.length() - countDigit(commCount)) +
                        commCount + emptySpace(gap2 - countDigit(commCost)) +
                        commCost
        );
        System.out.println(
                item2 + emptySpace(gap1 - item2.length() - countDigit(fuel)) +
                        fuel + emptySpace(gap2 - countDigit(fuelCost)) +
                        fuelCost
        );
        System.out.println(
                item3 + emptySpace(gap1 - item3.length() - countDigit(uncleared)) +
                        uncleared + emptySpace(gap2 - countDigit(unclearedCost)) +
                        unclearedCost

        );
        System.out.println(
                item4 + emptySpace(gap1 - item4.length() - countDigit(protectedTreeDestroyed)) +
                        protectedTreeDestroyed + emptySpace(gap2 - countDigit(treeCost)) +
                        treeCost
        );
        System.out.println(
                item5 + emptySpace(gap1 - item5.length() - countDigit(damage)) +
                        damage + emptySpace(gap2 - countDigit(damageCost)) +
                        damageCost
        );
        System.out.println("----");
        System.out.println(total + emptySpace(gap1 + gap2 - total.length() - countDigit(sum)) + sum + "\n");
    }

    /**
     * Generate empty spaces.
     *
     * @param length The amount of empty spaces desired.
     * @return A String of empty spaces.
     */
    private String emptySpace(int length) {
        return new String(new char[length]).replace("\0", " ");
    }

    /**
     * Count the number of digits for a number.
     *
     * @param num The number of interest.
     * @return The number of digits that the provided number has. For example, for 0 the returned number is
     * 1 and for 153 the returned number is 3.
     */
    private int countDigit(int num) {
        if (num < 100) {
            return num < 10 ? 1 : 2;
        }
        if (num < 10000) {
            return num < 1000 ? 3 : 4;
        }
        return (int) (Math.log10(num) + 1);
    }
}
