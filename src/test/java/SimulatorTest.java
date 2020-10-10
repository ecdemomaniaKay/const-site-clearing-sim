import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.*;


public class SimulatorTest {
    static final String MAP = "testFiles\\testSite.txt";
    Simulator simulator;

    @BeforeEach
    void init() {
        try {
            simulator = new Simulator(MAP);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    void givenProgramStarted_printWelcomeMessageAndSiteMap_whenInvokeWelcome() throws Exception {
        String text = tapSystemOutNormalized(() -> simulator.welcome());
        String expected = "\nWelcome to the Aconex site clearing simulator. This is a map of the site:\n" +
                "\n" +
                "t o t o o o o o o o\n" +
                "o o o o o o o T o o\n" +
                "r r r o o o o T o o\n" +
                "r r r r o o o o o o\n" +
                "r r r r r t o o o o\n" +
                "\n" +
                "The bulldozer is currently located at the Northern edge of the" +
                " site, immediately to the West of the site, and facing East.\n" +
                "\n";
        assertEquals(expected, text);
    }

    @Test
    void userInputCommand_trimsAndMakeLowercaseString_whenInvokeAddCommand() throws Exception {
        withTextFromSystemIn(" L  ")
                .execute(() -> assertEquals("l", simulator.addCommand()));
    }

}
