package za.co.wethinkcode.server;

/**
 * The type Dump command i.
 */
public class DumpCommandI implements IWorldCommand {

  @Override
  public String execute(World world) {
    return world.toString();
  }
}
