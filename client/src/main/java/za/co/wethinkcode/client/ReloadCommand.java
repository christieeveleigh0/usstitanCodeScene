package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Reload command.
 */
public class ReloadCommand extends Command {

  /**
   * Instantiates a new Reload command.
   */
  public ReloadCommand() {
    super("reload");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    if (robot.getMake().equals("bomber")) {
      robot.setOutput(
          colour.yellow(
              "<!><!><!><!>  Unauthorised Request <!><!><!><1><1>\n"
                  + "The BOMBER-BOT is not authorised to have any firearms.\n"
                  + "<!><!><!><!>  Unauthorised Request <!><!><!><1><1>"));
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
          if (responseFromServer.get("state").get("status").asText().equals("RELOAD")) {
            robot.setOutput(
                colour.green(
                    ">>> "
                        + robot.getMake()
                        + "-"
                        + robot.getName()
                        + " reloaded in "
                        + robot.getReload()
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
