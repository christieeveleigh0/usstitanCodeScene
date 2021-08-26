package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collections;
import java.util.List;

/**
 * The type Look command.
 */
public class LookCommand extends Command {

  /**
   * Instantiates a new Look command.
   */
  public LookCommand() {
    super("look");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    StringBuilder output =
        new StringBuilder(
            ">>> This is what " + robot.getMake() + "-" + robot.getName() + " can see:");
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
          int size = responseFromServer.get("data").get("objects").size();

          if (size > 0) {
            List<String> objDirection =
                new java.util.ArrayList<>(
                    Collections.singletonList(
                        responseFromServer
                            .get("data")
                            .get("objects")
                            .get(0)
                            .get("direction")
                            .asText()));
            List<String> objType =
                new java.util.ArrayList<>(
                    Collections.singletonList(
                        responseFromServer.get("data").get("objects").get(0).get("type").asText()));
            List<String> objDistance =
                new java.util.ArrayList<>(
                    Collections.singletonList(
                        responseFromServer
                            .get("data")
                            .get("objects")
                            .get(0)
                            .get("distance")
                            .asText()));

            for (int i = 1; i < size; i++) {
              objDirection.add(
                  responseFromServer.get("data").get("objects").get(i).get("direction").asText());
              objType.add(
                  responseFromServer.get("data").get("objects").get(i).get("type").asText());
              objDistance.add(
                  responseFromServer.get("data").get("objects").get(i).get("distance").asText());
            }
            for (int x = 0; x < size; x++) {
              output
                  .append("\n**************************")
                  .append("\nObject:    ")
                  .append(objType.get(x))
                  .append(".")
                  .append("\nDirection: ")
                  .append(objDirection.get(x))
                  .append(".")
                  .append("\nDistance:  ")
                  .append(objDistance.get(x))
                  .append(" steps.");
            }
          } else {
            output
                .append("\n**************************")
                .append("\nThere's nothing for the next ")
                .append(robot.getVisibility())
                .append(" steps.");
          }

          robot.setOutput(colour.green(output.toString()));
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
