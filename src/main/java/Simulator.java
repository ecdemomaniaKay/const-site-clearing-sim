import java.util.Scanner;

public class Simulator {
    private final Site site;
    private final Bulldozer bulldozer;
    private int commIssued;
    private int protectedTreeDestroyed;


    Simulator(String map) {
        site = new Site(map);
        bulldozer = new Bulldozer();
        commIssued = 0;
        protectedTreeDestroyed = 0;
    }

    public void welcome() {
        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:\n");
        site.display();
        System.out.println();
        System.out.println("The bulldozer is currently located at the Northern edge of the" +
                " site, immediately to the West of the site, and facing East.\n");
    }

    public boolean addCommand() {
        Scanner input = new Scanner(System.in);
        System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
        String command = input.nextLine().trim();
        switch (command.toLowerCase().trim()) {
            case "l":
                bulldozer.turn('L');
                commIssued += 1;
                break;
            case "r":
                bulldozer.turn('R');
                commIssued += 1;
                break;
            case "q":
                terminate("at your request");
                return true;
            default:
                if (command.matches("a\\s+[1-9]\\d*")) {
                    int distance = Integer.parseInt(command.split("\\s+")[1]);
                    int[] position = bulldozer.getPosition();
                    char orientation = bulldozer.getOrientation();

                    String route = site.getRoute(position, orientation, distance);
                    String checked = site.checkForProtectedTree(route);
                    String cleared = bulldozer.advance(checked);
                    site.setRowCol(position, orientation, cleared);

                    // debug
                    site.display();
                    System.out.println("pos: [" + bulldozer.getPosition()[0] + ", " + bulldozer.getPosition()[1] + "]");
                    System.out.println("orientation: " + bulldozer.getOrientation());

                    commIssued += 1;

                    if (checked.length() != route.length()) {
                        protectedTreeDestroyed = 1;
                        terminate("because you attempted to removed a protected tree");
                        return true;
                    } else if (route.contains("OutOfBoundsError") || route.length() != distance) {
                        terminate("because you attempted to navigated beyond the boundary of the site");
                        return true;
                    }
                } else {
                    System.out.println(command + " is an invalid command. Please try again.");
                }
        }

        return false;
    }

    private void terminate(String reason) {
        System.out.println();
        System.out.println("The simulation has ended " + reason + ". These are the commands you issued:\n");
        // todo: print command summary
        System.out.println("The costs for this land clearing operation were:\n");
        costSummary();
        System.out.println("Thank you for using the Aconex site clearing simulator.");
    }

    private void costSummary() {
        int commUnitCost = 1;
        int fuelUnitCost = 1;
        int unclearedUnitCost = 3;
        int protectedTreeUnitCost = 10;
        int damageUnitCost = 2;

        // width of the summary table
        int gap1 = 42;
        int gap2 = 8;

        String label1 = "Item";
        String label2 = "Quantity";
        String label3 = "Cost";
        String item1 = "communication overhead";
        String item2 = "fuel usage";
        String item3 = "uncleared squares";
        String item4 = "destruction of protected tree";
        String item5 = "paint damage to bulldozer";
        String total = "Total";
        int commCost = commIssued * commUnitCost;
        int fuel = bulldozer.getFuelUsage();
        int fuelCost = bulldozer.getFuelUsage() * fuelUnitCost;
        int uncleared = site.countUncleared();
        int unclearedCost = site.countUncleared() * unclearedUnitCost;
        int treeCost = protectedTreeDestroyed * protectedTreeUnitCost;
        int damage = bulldozer.getDamage();
        int damageCost = bulldozer.getDamage() * damageUnitCost;
        int sum = commCost + fuelCost + unclearedCost + treeCost + damageCost;

        System.out.println(
                label1 + emptySpace(gap1 - label1.length() - label2.length()) +
                        label2 + emptySpace(gap2 - label3.length()) +
                        label3);
        System.out.println(
                item1 + emptySpace(gap1 - item1.length() - countDigit(commIssued)) +
                        commIssued + emptySpace(gap2 - countDigit(commCost)) +
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

    private String emptySpace(int length) {
        return new String(new char[length]).replace("\0", " ");
    }

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
