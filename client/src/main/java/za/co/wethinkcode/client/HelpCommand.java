package za.co.wethinkcode.client;

/**
 * Command menu.
 */
public class HelpCommand extends Command {

  /**
   * Instantiates a new Help command.
   */
  public HelpCommand() {
    super("help");
  }

  @Override
  public boolean execute(Robot robot, Comms comms) {
    Colour colour = new Colour();
    robot.setOutput(
        colour.green(
            "# See Wiki for detailed info -"
                + " https://github.com/wtc-cohort-2020/0029-robot-worlds/wiki/Robot-types-and-Commands.\n\n"
                + "------------------------------ Command List"
                + " -----------------------------------\n"
                + "-------------------------------------------------------------------------------\n"
                + ": [HELP]           displays what you're reading right now\n"
                + ": [QUIT]           exits the world\n"
                + ": [LAUNCH]         send specific robot into the world e.g. 'LAUNCH SNIPER"
                + " VUYO'\n"
                + ": [FORWARD]        move forward by specified number of steps, e.g. 'FORWARD"
                + " 10'\n"
                + ": [BACK]           move back by specified number of steps, e.g. 'BACK 10'\n"
                + ": [LEFT]           turn left\n"
                + ": [RIGHT]          turn right\n"
                + ": [LOOK]           look around in all four directions\n"
                + ": [REPAIR]         repairs the robot's shields to maximum strength\n"
                + ": [FIRE]           fires the robot's gun\n"
                + ": [RELOAD]         reloads the robot's gun\n"
                + ": [MINE]           places a mine\n"
                + "-------------------------------------------------------------------------------\n"
                + "-------------------------------------------------------------------------------\n"));
    return true;
  }
}
