package za.co.wethinkcode.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisplayStateTest {
  public Colour colour = new Colour();
  public Robot robot = new Robot("sniper", "hal", 1, 2);

  @Test
  void clientRequest() throws JsonProcessingException {
    JsonNode stateResponse =
        new ObjectMapper()
            .readTree(
                "{\"position\": [0,0],"
                    + "\"direction\": \"NORTH\","
                    + "\"shields\": 3,"
                    + "\"shots\": 2,"
                    + "\"status\": \"NORMAL\"}");
    DisplayState state = new DisplayState("client", stateResponse, robot);

    assertTrue(state.shouldContinue());
    assertEquals(
        colour.green(
            ">>> sniper-hal's Diagnostics report.\n"
                + "\tPosition:     [0,0]\n"
                + "\tDirection:    \"NORTH\"\n"
                + "\tShields left: 3\n"
                + "\tShots left:   2"),
        robot.getOutput());
  }

  @Test
  void serverRequest() throws JsonProcessingException {
    JsonNode stateResponse =
        new ObjectMapper()
            .readTree(
                "{\"position\": [0,0],"
                    + "\"direction\": \"NORTH\","
                    + "\"shields\": 2,"
                    + "\"shots\": 2,"
                    + "\"status\": \"NORMAL\"}");
    DisplayState state = new DisplayState("server", stateResponse, robot);

    assertTrue(state.shouldContinue());
    assertEquals(
        colour.red("<!><!><!><!><!> YOU'VE BEEN SHOT!!! <!><!><!><!><!>\n")
            + colour.green(
                ">>> sniper-hal's Diagnostics report.\n"
                    + "\tPosition:     [0,0]\n"
                    + "\tDirection:    \"NORTH\"\n"
                    + "\tShields left: 2\n"
                    + "\tShots left:   2"),
        robot.getOutput());
  }

  @Test
  void deadRequest() throws JsonProcessingException {
    JsonNode stateResponse =
        new ObjectMapper()
            .readTree(
                "{\"position\": [0,0],"
                    + "\"direction\": \"NORTH\","
                    + "\"shields\": -1,"
                    + "\"shots\": 2,"
                    + "\"status\": \"DEAD\"}");
    DisplayState state = new DisplayState("server", stateResponse, robot);

    assertTrue(!state.shouldContinue());
    assertEquals(
        colour.red(
            "<!><!><!><!><!> YOU'RE DEAD  !!! <!><!><!><!><!>\n"
                + "     You've been shot and died.\n"
                + "<!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!>"),
        robot.getOutput());
  }
}
