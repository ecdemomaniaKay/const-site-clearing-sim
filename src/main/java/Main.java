public class Main {
    public static void main(String... args) {
        Simulator sim = new Simulator("testFiles\\testSite.txt");
        sim.welcome();
        boolean exit = false;
        while (!exit) {
            exit = sim.addCommand();
        }
    }
}
