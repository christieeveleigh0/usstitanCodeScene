package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type State command.
 */
public class StateCommand extends Command {

  /**
   * Instantiates a new Mine command.
   */
  public StateCommand() {
    super("state");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
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

    DisplayState state = new DisplayState("client", responseFromServer, robot);
    return state.shouldContinue();
  }
}
