package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Forward command.
 */
public class ForwardCommand extends Command {

  /**
   * Instantiates a new Forward command.
   *
   * @param argument the number of steps.
   */
  public ForwardCommand(String argument) {
    super("forward", argument);
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", robot.getName());
      request.put("command", getName());
      request.putArray("arguments").add(Integer.parseInt(getArgument()));
      comms.setRequest(request);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    comms.sendRequest();
    JsonNode responseFromServer = comms.getResponse();

    while (true) {
      try {
        if (responseFromServer.get("result").asText().equals("OK")) {
          switch (responseFromServer.get("data").get("message").asText()) {
            case "Done":
              String position =
                  responseFromServer.get("state").get("position").get(0).asText()
                      + ","
                      + responseFromServer.get("state").get("position").get(1).asText();
              robot.setOutput(
                  colour.green(
                      ">>> "
                          + robot.getMake()
                          + "-"
                          + robot.getName()
                          + " Moved forward "
                          + getArgument()
                          + "\n   Now at position ["
                          + position
                          + "]."));
              return true;
            case "Obstructed":
              robot.setOutput(colour.green(">>> There seems to be an obstacle in the way."));
              return true;
            case "Fell":
              robot.setOutput(
                  colour.red(
                      "<x><x><x><x><x> YOU'RE DEAD  !!! <x><x><x><x><x>\n"
                          + "     You've fallen into a pit and died.\n"
                          + "<x><x><x><x><x><x><x><x><x><x><x><x><x><x><x><x>"));
              return false;
            case "Mine":
              robot.setOutput(
                  colour.green(
                      ">>> "
                          + robot.getMake()
                          + "-"
                          + robot.getName()
                          + " stepped on a mine but survived."));
              if (responseFromServer.get("state").get("status").asText().equals("DEAD")) {
                robot.setOutput(
                    colour.red(
                        "<x><x><x><x><x> YOU'RE DEAD  !!! <x><x><x><x><x>\n"
                            + "     You've been shot and died.\n"
                            + "<x><x><x><x><x><x><x><x><x><x><x><x><x><x><x><x>"));
                return false;
              }
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
