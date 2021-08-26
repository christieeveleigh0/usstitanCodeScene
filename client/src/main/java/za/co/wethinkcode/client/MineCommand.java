package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Mine command.
 */
public class MineCommand extends Command {

  /**
   * Instantiates a new Mine command.
   */
  public MineCommand() {
    super("mine");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    if (!robot.getMake().equals("bomber")) {
      robot.setOutput(
          colour.yellow(
              "<!><!><!><!>  Unauthorised Request <!><!><!><1><1>\n"
                  + "Only the BOMBER-BOT is authorised to place mines.\n"
                  + "<!><!><!><!>  Unauthorised Request <!><!><!><1><1>\n"));
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
          if (responseFromServer.get("state").get("status").asText().equals("SETMINE")) {
            robot.setOutput(
                colour.green(
                    ">>> "
                        + robot.getMake()
                        + "-"
                        + robot.getName()
                        + " placed a mine in "
                        + robot.getMine()
                        + " seconds."));
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
