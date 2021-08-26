package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The type Display state.
 */
public class DisplayState {
  private boolean state = true;
  private Colour colour = new Colour();

  /**
   * Sends a output message of the robots current state.
   *
   * @param who   who is requesting the state. Client or Server.
   * @param state the JSON state string.
   * @param robot the robot that we want the state of.
   */
  public DisplayState(String who, JsonNode state, Robot robot) {
    try {
      String report = "";
      String status = state.get("status").asText();
      int shields = state.get("shields").asInt();

      if ((status.equals("SETMINE")) && (shields < 0)) {
        robot.setOutput(
            colour.red(
                "<!><!><!><!><!> YOU'RE DEAD  !!! <!><!><!><!><!>\n"
                    + "     You've stepped on a mine and died.\n"
                    + "<!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!>"));
        this.state = false;
      } else if (status.equals("SETMINE")) {
        report = colour.red("<!><!><!><!> YOU'VE STEPPED ON A MINE!!! <!><!><!><!>\n");
        this.state = true;
      } else if (status.equals("DEAD")) {
        robot.setOutput(
            colour.red(
                "<!><!><!><!><!> YOU'RE DEAD  !!! <!><!><!><!><!>\n"
                    + "     You've been shot and died.\n"
                    + "<!><!><!><!><!><!><!><!><!><!><!><!><!><!><!><!>"));
        this.state = false;
      } else if (who.equals("server")) {
        report = colour.red("<!><!><!><!><!> YOU'VE BEEN SHOT!!! <!><!><!><!><!>\n");
        this.state = true;
      }
      if (this.state) {
        String position =
            state.get("position").get(0).asText() + "," + state.get("position").get(1).asText();
        robot.setOutput(
            report
                + colour.green(
                    ">>> "
                        + robot.getMake()
                        + "-"
                        + robot.getName()
                        + "'s Diagnostics report."
                        + "\n\tPosition:     ["
                        + position
                        + "]\n\tDirection:    "
                        + state.get("direction")
                        + "\n\tShields left: "
                        + state.get("shields")
                        + "\n\tShots left:   "
                        + state.get("shots")));
      }
    } catch (Exception ex) { // This is if the server doesn't send a state back
      robot.setOutput(
          colour.purple(
              "<T11101> It appears we've lost communication with the base world.\n"
                  + "Perhaps we should try again."));
    }
  }

  /**
   * Should continue boolean.
   *
   * @return the boolean
   */
  public Boolean shouldContinue() {
    return this.state;
  }
}
