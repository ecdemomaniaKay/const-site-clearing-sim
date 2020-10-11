import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * Class that runs the simulator.
 */
public class Main {
    /**
     * Main method.
     */
    public static void main(String... args) {
        boolean newSession = true;

        while (newSession) {
            Simulator sim;
            try {
                sim = new Simulator(args[0]);
            } catch (DataFormatException e) {
                System.out.println("\nInvalid site map. The rows from the provided site map have unequal lengths. The simulator will end now.\n");
                enterToExit();
//                e.printStackTrace();
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("\nMissing argument. Please include the directory of the site map as the first argument.\n");
//                e.printStackTrace();
                enterToExit();
                return;
            }
            sim.welcome();
            boolean exit = false;
            while (!exit) {
                String command = sim.addCommand();
                String reason = sim.action(command);
                exit = sim.terminate(reason);
            }

            newSession = newSession();
        }
    }

    /**
     * Asks user whether they want to run another session.
     *
     * @return Whether user wants to start a new session.
     */
    private static boolean newSession() {
        while (true) {
            System.out.print("Would you like to start a new session? (Y/N) ");
            final String response = new Scanner(System.in).nextLine().toUpperCase().trim();

            if (response.equals("N")) {
                System.out.println("\nSimulator terminated.");
                enterToExit();
                return false;
            } else if (response.equals("Y")) {
                return true;
            } else {
                System.out.println("Invalid response. Enter Y to start new session or N to exit.");
            }
        }
    }

    /**
     * Adds a pause before the program terminates.
     */
    private static void enterToExit() {
        System.out.println("\n\nPress enter to exit...\n");
        new Scanner(System.in).nextLine();
    }
}
