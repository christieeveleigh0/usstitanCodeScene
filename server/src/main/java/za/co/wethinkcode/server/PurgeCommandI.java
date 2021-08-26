 package za.co.wethinkcode.server;

 import java.io.IOException;

 /**
  * The type Purge command i.
  */
 public class PurgeCommandI implements IWorldCommand {

     private final String NOT_PURGED = "(((No purge))) purge command should be followed by a valid robot's name (((egruP oN)))";
     private String robotName;
     private String purged;
     private boolean noName;

     /**
      * Instantiates a new Purge command i.
      *
      * @param argument the argument
      */
     public PurgeCommandI(String argument) {
         robotName = argument.strip();
         purged = "(((Purged))) " + robotName + " has been scrapped (((degruP)))";
         noName = robotName.isBlank();
     }

     @Override
     public String execute(World world) {
         boolean found = world.getRobotMap().containsKey(robotName);
         if (noName || !found) {
            return NOT_PURGED;
         }

         ClientHandler clientHandler = world.getClient(robotName);
         try {
             clientHandler.disconnect();
         } catch (IOException | NullPointerException ignored) {
            return purged;
         }
         return purged;
     }
 }
