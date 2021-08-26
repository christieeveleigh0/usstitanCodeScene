package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.Map;

/**
 * The type Repair command.
 */
public class RepairCommand extends Command {
  /**
   * Instantiates a new Repair command.
   */
  public RepairCommand() {
    super("repair");
  }

  @Override
  public Object execute(Object object) {
    Robot robot = (Robot) object;
    robot.repair();
    String message = "Done";
    Map<String, Object> data = Collections.singletonMap("message", message);
    return new Response(data, robot.getState());
  }
}
