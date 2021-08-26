package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.Map;

/**
 * The type Turn command.
 */
public class TurnCommand extends Command {

  /**
   * Instantiates a new Turn command.
   *
   * @param argument the argument
   */
  public TurnCommand(String argument) {
    super("turn", argument);
  }

  @Override
  public Object execute(Object object) {
    World world = (World) object;
    String[] args = getArgument().toLowerCase().trim().split(" ");
    Robot robot = world.getRobot(args[0]);
    world.updateDirection(robot, args[1].equals("right"));
    String message = "Done";
    Map<String, Object> data = Collections.singletonMap("message", message);
    world.getMaze().updateRobots(robot.getName(), robot.getPosition().getX(), robot.getPosition().getY());
    return new Response(data, robot.getState());
  }
}
