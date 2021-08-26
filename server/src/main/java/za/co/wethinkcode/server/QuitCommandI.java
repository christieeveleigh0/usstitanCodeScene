 package za.co.wethinkcode.server;

 /**
  * The type Quit command i.
  */
 public class QuitCommandI implements IWorldCommand {

  @Override
  public String execute(World world) {
      return world.getQuit();
  }
 }
