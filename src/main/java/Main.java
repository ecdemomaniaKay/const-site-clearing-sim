import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        boolean newSession = true;

        while (newSession) {
            final Simulator sim = new Simulator(args[0]);
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

    private static boolean newSession() {
        while (true) {
            System.out.print("Would you like to start a new session? (Y/N) ");
            final String response = new Scanner(System.in).nextLine().toUpperCase().trim();

            if (response.equals("N")) {
                System.out.println("Simulator terminated.\n");
                return false;
            } else if (response.equals("Y")) {
                return true;
            } else {
                System.out.println("Invalid response. Please try again.");
            }
        }
    }
}
