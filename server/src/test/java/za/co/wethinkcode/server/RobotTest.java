package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

  @Test
  void initialPosition() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    Position robotPosition = robot.getPosition();
    Object statePosition = robot.getState().get("position");
    assertEquals(robotPosition.toString(), statePosition.toString());
    assertEquals(World.Direction.NORTH, robot.getCurrentDirection());
  }

  @Test
  void initialStatus() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    assertEquals(World.Mode.NORMAL, robot.getStatus());
  }

  @Test
  void exit() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    assertEquals(1, world.getPopulation());
    RequestObject exitObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"exit\",\"arguments\":[]}");
    Command exitCommand = Command.create(exitObject);
    exitCommand.execute(world);
    Robot robot = world.getFugitive("hal");
    assertEquals(World.Mode.FLED, robot.getStatus());
  }
}
