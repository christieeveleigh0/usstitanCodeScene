package za.co.wethinkcode.server;

/**
 * The type Exit command.
 */
public class ExitCommand extends Command {

  /**
   * Instantiates a new Exit command.
   *
   * @param argument the argument
   */
  public ExitCommand(String argument) {
    super("exit", argument);
  }

  @Override
  public Object execute(Object object) {
    World world = (World) object;
    Robot robot = world.getRobot(getArgument());
    robot.setStatus(World.Mode.FLED);
    String name = robot.getName();
    world.addFugitive(robot);
    world.removeRobot(name);
    world.freeSpace();
    return new GeneralResponse("Your exit sequence has cleared", "OK");
  }
}
