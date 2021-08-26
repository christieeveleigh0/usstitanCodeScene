package za.co.wethinkcode.server;

import java.util.Collections;
import java.util.Map;

/**
 * The type Reload command.
 */
public class ReloadCommand extends Command {

  /**
   * Instantiates a new Reload command.
   */
  public ReloadCommand() {
    super("reload");
  }

  @Override
  public Object execute(Object object) {
    Robot robot = (Robot) object;
    robot.reload();
    String message = "Done";
    Map<String, Object> data = Collections.singletonMap("message", message);
    return new Response(data, robot.getState());
  }
}
