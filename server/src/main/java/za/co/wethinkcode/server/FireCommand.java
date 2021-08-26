package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Fire command.
 */
public class FireCommand extends Command {

  /**
   * Instantiates a new Fire command.
   *
   * @param argument the argument
   */
  public FireCommand(String argument) {
    super("fire", argument);
  }

  @Override
  public Object execute(Object object) {
    World world = (World) object;
    String name = getArgument();
    Robot robot = world.getRobot(name);
    World.Direction robotDirection = robot.getCurrentDirection();
    int robotX = robot.getPosition().getX();
    int robotY = robot.getPosition().getY();
    Map<String, Object> data = new HashMap<>();
    String dataMessage = world.updatePosition(robot, robot.getShotsMax(), false, true).equals(World.UpdateResponse.HIT) ? "Hit" : "Miss";
    data.put("message", dataMessage);
    robot.minusShots();

    if (dataMessage.equals("Hit")) {
      List<String> hitList = robot.getHitList();
      String victimName = hitList.get(hitList.size() - 1);
      Robot victimRobot = world.getRobot(victimName);
      int distance = new Tracker(robotDirection).getStepDistance(robotDirection, robotX, robotY, victimRobot.getPosition().getX(), victimRobot.getPosition().getY());
      data.put("distance", distance);
      data.put("robot", victimName);
      data.put("state", victimRobot.getState());
    }
    Map<String, Object> state = Collections.singletonMap("shots", robot.getShots());
    return new Response(data, state);
  }
}
