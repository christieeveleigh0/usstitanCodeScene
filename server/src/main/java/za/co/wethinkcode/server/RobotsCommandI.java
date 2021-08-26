 package za.co.wethinkcode.server;

 import java.util.HashMap;
 import java.util.Map;

 /**
  * The type Robots command i.
  */
 public class RobotsCommandI implements IWorldCommand {

     public String execute(World world) {
         Map<String, Robot> robots = world.getRobotMap();
         if (robots.isEmpty()) {
             return "(((No robots))) there are no robots on this world (((stobor oN)))";
         }
         Map<String, Object> map = new HashMap<>();
         robots.forEach((k, v) -> map.put(k,v.getState()));
         return "(((Robots)))\n" + map + "\n(((stoboR)))";
     }
 }
