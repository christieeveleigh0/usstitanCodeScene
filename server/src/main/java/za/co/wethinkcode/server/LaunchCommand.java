package za.co.wethinkcode.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Launch command.
 */
public class LaunchCommand extends Command {

  /**
   * Instantiates a new Launch command.
   *
   * @param argument the argument
   */
  public LaunchCommand(String argument) {
    super("launch", argument);
  }

  @Override
  public Object execute(Object object) {
    World world = (World) object;
    String[] args = getArgument().toLowerCase().trim().split(" ");
    String make = args[0];
    String robotName = args[1];
    Integer clientHits = Integer.parseInt(args[2]);
    Integer clientBullets = Integer.parseInt(args[3]);
    Integer hits = world.getShieldStrength();
    Integer bullets = world.getShots();
    World.Mode mode = World.Mode.NORMAL;

    if (world.getRobotMap().size() > 0) {
      if (world.getRobotMap().containsKey(args[1])) {
        return new GeneralResponse("Too many of you in this world");
      }
    }

    if (!world.isSpace()) {
      return new GeneralResponse("No more space in this world");
    }

    Position randomPosition = world.randomPosition();
    List<Integer> coordinates = new ArrayList<>();
    coordinates.add(randomPosition.getX());
    coordinates.add(randomPosition.getY());
    Integer steps = world.getVisibility();
    Integer reloadSeconds = world.getReloadTime();
    Integer repairSeconds = world.getRepairTime();
    Integer mineSeconds = world.getMineSet();
    hits = (clientHits < hits) ? clientHits : hits;
    bullets = (clientBullets < bullets) ? clientBullets : bullets;
    World.Direction facing = world.getStartDirection();

    Map<String, Object> data = new HashMap<>();
    data.put("position", coordinates);
    data.put("visibility", steps);
    data.put("reload", reloadSeconds);
    data.put("repair", repairSeconds);
    data.put("mine", mineSeconds);
    data.put("shields", hits);

    Map<String, Object> state = new HashMap<>();
    state.put("position", coordinates);
    state.put("direction", facing);
    state.put("shields", hits);
    state.put("shots", bullets);
    state.put("status", mode);

    Robot robot = new Robot(make, robotName, data, state);
    robot.setPosition(randomPosition);
    world.getMaze().updateRobots(robotName, randomPosition.getX(), randomPosition.getY());
    robot.setShotsMax(bullets);
    robot.setShieldMax(world.getShieldStrength());
    robot.setRepairTime(world.getRepairTime());
    robot.setReloadTime(world.getReloadTime());
    robot.setMineTime(world.getMineSet());
    world.addRobot(args[1], robot);
    world.crowdSpace();

    world.getMaze().addRobotObstacle(new RobotObstacle(robotName, randomPosition.getX(), randomPosition.getY()));
    return new Response(data, state);
  }
}
