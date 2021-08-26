package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.Map;

/**
 * The type Back command.
 */
public class BackCommand extends Command {

  /**
   * Instantiates a new Back command.
   *
   * @param argument the argument
   */
  public BackCommand(String argument) {
    super("back", argument);
  }

  @Override
  public Object execute(Object object) {
    World world = (World) object;
    String[] args = getArgument().toLowerCase().trim().split(" ");
    Robot robot = world.getRobot(args[0]);
    int steps = Integer.parseInt(args[1]);
    World.UpdateResponse updateResponse = world.updatePosition(robot, -steps, false, false);
    String message;
    switch (updateResponse) {
      case FAILED_OUTSIDE_WORLD:
      case FAILED_OBSTRUCTED:
      case FAILED_ROBOT:
        message = "Obstructed";
        break;
      case FAILED_PIT:
        message = "Fell";
        break;
      case FAILED_MINE:
        message = "Mine";
        break;
      default:
        message = "Done";
    }

    Map<String, Object> data = Collections.singletonMap("message", message);
    world.getMaze().updateRobots(robot.getName(), robot.getPosition().getX(), robot.getPosition().getY());
    return new Response(data, robot.getState());
  }
}
