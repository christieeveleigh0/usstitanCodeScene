package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Quit command.
 */
public class QuitCommand extends Command {

  /**
   * Instantiates a new Quit command.
   */
  public QuitCommand() {
    super("quit");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
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
    // We don't need a response as we are only telling the server that we are quitting.

    robot.setOutput(colour.purple("<T11101> Time to leave this place."));
    return false;
  }
}
