package za.co.wethinkcode.server;

/** Command menu. */
public class HelpCommandI implements IWorldCommand {

  @Override
  public String execute(World world) {
    return world.getHelp();
  }
}
