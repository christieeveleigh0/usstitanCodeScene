package za.co.wethinkcode.server;

import java.util.*;

/**
 * The type Look command.
 */
public class LookCommand extends Command {

  /**
   * Instantiates a new Look command.
   *
   * @param argument the argument
   */
  public LookCommand(String argument) {
    super("look", argument);
  }

  @Override
  public Object execute(Object object) {

    World world = (World) object;
    String name = getArgument();
    Robot robot = world.getRobot(name);
    robot.clearLookerList();
    int distance = world.getVisibility();
    World.Direction originalDirection = robot.getCurrentDirection();

    do {
      world.updatePosition(robot, distance, true, false);
      world.updateDirection(robot, true);
    } while (!robot.getCurrentDirection().equals(originalDirection));

    Map<String, Object> data = new HashMap<>();
    data.put("objects", robot.getLookerList());
    Map<String, Object> state = Collections.singletonMap("state", robot.getState());
    return new Response(data, state);
  }
}
