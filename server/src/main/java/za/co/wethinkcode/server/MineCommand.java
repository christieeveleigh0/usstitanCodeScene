package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.Map;

/**
 * The type Mine command.
 */
public class MineCommand extends Command {
  /**
   * Instantiates a new Mine command.
   *
   * @param argument the argument
   */
  public MineCommand(String argument) {
    super("mine", argument);
  }

  @Override
  public Object execute(Object object) {
    World world = (World) object;
    String name = getArgument();
    Robot robot = world.getRobot(name);
    Mine mine = robot.setMine();

    world.getMaze().addMineObstacle(mine);

    world.updatePosition(robot, 1, false, false);

    String message = "Done";
    Map<String, Object> data = Collections.singletonMap("message", message);
    return new Response(data, robot.getState());
  }
}
