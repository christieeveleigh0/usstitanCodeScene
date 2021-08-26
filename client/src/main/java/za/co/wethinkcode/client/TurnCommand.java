package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Left command.
 */
public class TurnCommand extends Command {

  /**
   * Instantiates a new Left command.  @param argument the argument
   */
  public TurnCommand(String argument) {
    super("turn", argument);
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    try {
      ObjectMapper mapper = new ObjectMapper();

      ObjectNode request = mapper.createObjectNode();
      request.put("robot", robot.getName());
      request.put("command", getName());
      request.putArray("arguments").add(getArgument());
      comms.setRequest(request);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    comms.sendRequest();
    JsonNode responseFromServer = comms.getResponse();

    while (true) {
      try {
        if (responseFromServer.get("result").asText().equals("OK")) {
          robot.setOutput(
              colour.green(
                  ">>> "
                      + robot.getMake()
                      + "-"
                      + robot.getName()
                      + " Turned "
                      + getArgument()
                      + "."));
          return true;
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
