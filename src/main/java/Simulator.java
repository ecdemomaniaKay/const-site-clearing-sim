import java.util.Scanner;

public class Simulator {
    private final Site site;
    private final Bulldozer bulldozer;
    private final int commCost = 1;
    private final int fuelCost = 1;
    private final int unclearedSquareCost = 3;
    private final int protectedTreeCost = 10;
    private final int paintDamageCost = 2;
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

    public void addCommand() {
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
                break;
            default:
                if (command.matches("a\\s+[1-9]\\d*")) {
                    int distance = Integer.parseInt(command.split("\\s+")[1]);
                    String route = site.getRoute(bulldozer.getPosition(), bulldozer.getOrientation(), distance);
                    String checked = site.checkForProtectedTree(route);
                    bulldozer.advance(checked);
                    commIssued += 1;
                    if (checked.length() != route.length()) {
                        protectedTreeDestroyed = 1;
                        terminate("because you have attempted to remove a protected tree");
                    }
                } else {
                    System.out.println(command + " is an invalid command. Please try again.");
                }
        }
    }

    private void terminate(String reason) {
        System.out.println();
        System.out.println("The simulation has ended " + reason + ". These are the commands you issued:\n");
        // todo: print command summary
        System.out.println("The costs for this land clearing operation were:\n");
        summary();
        System.out.println("Thank you for using the Aconex site clearing simulator.");
    }

    private void summary() {
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
        int comm = commIssued * commCost;
        int fuel = bulldozer.getFuelUsage() * fuelCost;
        int uncleared = 0;
        int proTree = protectedTreeDestroyed * protectedTreeCost;
        int damage = bulldozer.getDamage() * paintDamageCost;
        int sum = comm + fuel + uncleared + proTree + damage;

        System.out.println(
                label1 + emptySpace(gap1 - label1.length() - label2.length()) +
                        label2 + emptySpace(gap2 - label3.length()) +
                        label3);
        System.out.println(
                item1 + emptySpace(gap1 - item1.length() - countDigit(commIssued)) +
                        commIssued + emptySpace(gap2 - countDigit(comm)) +
                        comm
        );
        System.out.println(
                item2 +
                        emptySpace(gap1 - item2.length() - countDigit(bulldozer.getFuelUsage())) +
                        bulldozer.getFuelUsage() +
                        emptySpace(gap2 - countDigit(fuel)) +
                        fuel
        );
        System.out.println("uncleared squares                       12      12");
        System.out.println(
                item4 +
                        emptySpace(gap1 - item4.length() - countDigit(protectedTreeDestroyed)) +
                        protectedTreeDestroyed +
                        emptySpace(gap2 - countDigit(proTree)) +
                        proTree
        );
        System.out.println(
                item5 +
                        emptySpace(gap1 - item5.length() - countDigit(bulldozer.getDamage())) +
                        bulldozer.getDamage() +
                        emptySpace(gap2 - countDigit(damage)) +
                        damage
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
