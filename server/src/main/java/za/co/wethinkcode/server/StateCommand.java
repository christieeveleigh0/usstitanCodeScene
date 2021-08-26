package za.co.wethinkcode.server;

/**
 * The type State command.
 */
public class StateCommand extends Command {

  /**
   * Instantiates a new State command.
   */
  public StateCommand() {
    super("state");
  }

  @Override
  public Object execute(Object object) {
    return ((Robot) object).getState();
  }
}
