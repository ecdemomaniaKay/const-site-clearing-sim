import java.util.Scanner;

public class Simulator {
    private Site site;
    private Bulldozer bulldozer;

    Simulator(String map) {
        site = new Site(map);
        bulldozer = new Bulldozer();
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
                break;
            case "r":
                bulldozer.turn('R');
                break;
            case "q":
                terminate("at your request");
                break;
            default:
                if (command.matches("a\\s+[1-9]\\d*")) {
//                    bulldozer.advance(Integer.parseInt(command.split("\\s+")[1]));
                } else {
                    System.out.println(command + " is an invalid command. Please try again.");
                }
        }
    }

    public void terminate(String reason) {
        System.out.println("The simulation has ended " + reason + ". These are the commands you issued:\n");
        // todo: print command summary
        System.out.println("The costs for this land clearing operation were:\n");
        // todo: print cost summary
        System.out.println("Thank you for using the Aconex site clearing simulator.");
    }

}
