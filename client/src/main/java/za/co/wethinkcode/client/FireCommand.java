package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Fire command.
 */
public class FireCommand extends Command {

  /**
   * Instantiates a new Fire command.
   */
  public FireCommand() {
    super("fire");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    if (robot.getMake().equals("bomber")) {
      robot.setOutput(
          colour.yellow(
              "<!><!><!><!>  Unauthorised Request  <!><!><!><!><!>\n"
                  + "The BOMBER-BOT is not authorised to have any firearms.\n"
                  + "<!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!>"));
      return true;
    }

    try {
      ObjectMapper mapper = new ObjectMapper();

      ObjectNode request = mapper.createObjectNode();
      request.put("robot", robot.getName());
      request.put("command", getName());
      request.putArray("arguments");
      comms.setRequest(request);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    comms.sendRequest();
    JsonNode responseFromServer = comms.getResponse();

    while (true) {
      try {
        if (responseFromServer.get("result").asText().equals("OK")) {
          if (responseFromServer.get("data").get("message").asText().equals("Hit")) {
            robot.setOutput(
                colour.green(
                    ">>> Shots fired!\n"
                        + "   You hit "
                        + responseFromServer.get("data").get("robot").asText()
                        + "."));
            return true;
          } else {
            robot.setOutput(colour.green(">>> Shots fired!\n" + "   You missed."));
            return true;
          }
        }
      } catch (NullPointerException e) {
        DisplayState state = new DisplayState("server", responseFromServer, robot);
        if (state.shouldContinue()) {
          responseFromServer = comms.getResponse();
        } else {
          return false;
        }
      } catch (IndexOutOfBoundsException e) {
        robot.setOutput(
            colour.purple(
                "<T11101> It appears we've lost communication with the base world.\n"
                    + "Perhaps we should try again..."));
        return true;
      }
    }
  }
}
