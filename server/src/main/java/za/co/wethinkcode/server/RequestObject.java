package za.co.wethinkcode.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * The type Request object.
 */
public class RequestObject {
  /**
   * The Gson.
   */
  static final Gson gson = new Gson();
  private String robot;
  private String command;
  private String[] arguments;

  /**
   * Instantiates a new Request object.
   *
   * @param requestString the request string
   */
  public RequestObject(String requestString) {
    JsonObject requestObject = gson.fromJson(requestString, JsonObject.class);
    String robotString = requestObject.get("robot").toString();
    this.robot = robotString.substring(1, robotString.length() - 1);
    String commandString = requestObject.get("command").toString();
    this.command = commandString.substring(1, commandString.length() - 1);
    this.arguments = requestObject.get("arguments").toString().split(",");
  }

  /**
   * Gets robot.
   *
   * @return the robot
   */
  public String getRobot() {
    return robot;
  }

  /**
   * Gets command.
   *
   * @return the command
   */
  public String getCommand() {
    return command;
  }

  /**
   * Get arguments string [ ].
   *
   * @return the string [ ]
   */
  public String[] getArguments() {
    return arguments;
  }
}
