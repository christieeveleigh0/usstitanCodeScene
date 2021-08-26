package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Launch command.
 */
public class LaunchCommand extends Command {

  /**
   * Instantiates a new Launch command.
   *
   * @param argument the robot's make and name.
   */
  public LaunchCommand(String argument) {
    super("launch", argument);
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    if (robot.getLaunched()) {
      robot.setOutput(
          colour.white("<" + robot.getMake() + "-" + robot.getName() + "> I've already launched."));
      return true;
    }
    try {
      ObjectMapper mapper = new ObjectMapper();

      ObjectNode request = mapper.createObjectNode();
      request.put("robot", robot.getName());
      request.put("command", getName());
      request
          .putArray("arguments")
          .add(robot.getMake())
          .add(robot.getShield())
          .add(robot.getAmmo());
      comms.setRequest(request);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    comms.sendRequest();
    JsonNode responseFromServer = comms.getResponse();

    if (responseFromServer.get("result").asText().equals("OK")) {
      robot.setVisibility(responseFromServer.get("data").get("visibility").asInt());
      robot.setReload(responseFromServer.get("data").get("reload").asInt());
      robot.setRepair(responseFromServer.get("data").get("repair").asInt());
      robot.setMine(responseFromServer.get("data").get("mine").asInt());
      robot.setShieldHits(responseFromServer.get("data").get("shields").asInt());
      String position =
          responseFromServer.get("state").get("position").get(0).asText()
              + ","
              + responseFromServer.get("state").get("position").get(1).asText();

      robot.setOutput(
          colour.green(
              ">>> Launching "
                  + robot.getMake()
                  + "-"
                  + robot.getName()
                  + " at position ["
                  + position
                  + "]."
                  + "\n>>> Initialization report."
                  + "\n\tDirection:        "
                  + responseFromServer.get("state").get("direction")
                  + "\n\tShields capacity: "
                  + responseFromServer.get("state").get("shields")
                  + "\n\tShots capacity:   "
                  + responseFromServer.get("state").get("shots")));
      robot.setLaunched(true);

    } else if (responseFromServer.get("result").asText().equals("ERROR")) {
      switch (responseFromServer.get("data").get("message").asText()) {
        case "No more space in this world":
          robot.setOutput(colour.green(">>> It appears there is no more space in this world."));
          robot.setLaunched(false);
          break;
        case "Too many of you in this world":
          robot.setOutput(colour.green(">>> There is already a robot with that name."));
          robot.setLaunched(false);
          break;
      }

    } else { // This is if the server doesn't send a response back
      robot.setOutput(
          colour.purple(
              "<T11101> It appears we've lost communication with the base world.\n"
                  + "Perhaps we should try again..."));
      robot.setLaunched(false);
    }

    return true;
  }
}
