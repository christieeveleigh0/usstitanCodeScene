package za.co.wethinkcode.server;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

  Command getCommandDetails(String requestString) {
    return Command.create(new RequestObject(requestString));
  }

  @Test
  void createCommand() {
    Command launchCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    assertEquals("launch", launchCommand.getName());
    assertEquals("dummy hal 10 20", launchCommand.getArgument());

    Command stateCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"state\",\"arguments\":[]}");
    assertEquals("state", stateCommand.getName());
    assertEquals("", stateCommand.getArgument());

    Command exitCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"exit\",\"arguments\":[]}");
    assertEquals("exit", exitCommand.getName());
    assertEquals("hal", exitCommand.getArgument());

    Command quitCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"quit\",\"arguments\":[]}");
    assertEquals("exit", quitCommand.getName());
    assertEquals("hal", quitCommand.getArgument());

    Command forwardCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"forward\",\"arguments\":[5]}");
    assertEquals("forward", forwardCommand.getName());
    assertEquals("hal 5", forwardCommand.getArgument());

    Command backCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"back\",\"arguments\":[5]}");
    assertEquals("back", backCommand.getName());
    assertEquals("hal 5", backCommand.getArgument());

    Command rightCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"turn\",\"arguments\":[right]}");
    assertEquals("turn", rightCommand.getName());
    assertEquals("hal right", rightCommand.getArgument());

    Command leftCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"turn\",\"arguments\":[left]}");
    assertEquals("turn", leftCommand.getName());
    assertEquals("hal left" + "", leftCommand.getArgument());

    Command repairCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"repair\",\"arguments\":[]}");
    assertEquals("repair", repairCommand.getName());
    assertEquals("", repairCommand.getArgument());

    Command reloadCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"reload\",\"arguments\":[]}");
    assertEquals("reload", reloadCommand.getName());
    assertEquals("", reloadCommand.getArgument());

    Command mineCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"mine\",\"arguments\":[]}");
    assertEquals("mine", mineCommand.getName());
    assertEquals("hal", mineCommand.getArgument());

    Command fireCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"fire\",\"arguments\":[]}");
    assertEquals("fire", fireCommand.getName());
    assertEquals("hal", fireCommand.getArgument());

    Command lookCommand = getCommandDetails("{\"robot\":\"hal\",\"command\":\"look\",\"arguments\":[]}");
    assertEquals("look", lookCommand.getName());
    assertEquals("hal", lookCommand.getArgument());
  }

  @Test
  void getLaunchName() {
    Command test = new LaunchCommand("dummy hal 1 2");
    assertEquals("launch", test.getName());
    assertEquals("dummy hal 1 2", test.getArgument());
  }
  @Test
  void successfulLaunch() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    for (int i = 0; i <= 15; i++) { // space doesn't drop below 0
      world.freeSpace();
    }
    assertEquals(0, world.getPopulation());
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    assertTrue(launchDummyBot.execute(world) instanceof Response);
    Robot robot = world.getRobot("hal");
    assertEquals("hal", robot.getName());
    assertEquals("dummy", robot.getMake());
    String robotPosition =
        "[" + robot.getPosition().getX() + ", " + robot.getPosition().getY() + "]";
    String mapPosition = robot.getState().get("position").toString();
    assertEquals(robotPosition, mapPosition);
    assertEquals(robot.getState().get("direction"), robot.getCurrentDirection());
    Integer testShields = 3;
    assertEquals(testShields, robot.getShields());
    assertEquals(10, robot.getShots());
    assertEquals(robot.getStatus(), robot.getState().get("status"));
  }

  @Test
  void noSpaceLaunch() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    for (int i = 0; i <= 15; i++) { // space doesnt crowd above 8
      world.crowdSpace();
    }
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    GeneralResponse generalResponse = (GeneralResponse) launchDummyBot.execute(world);
    assertEquals("No more space in this world", generalResponse.getMessage());
    assertEquals(8, world.getPopulation());
  }

  @Test
  void tooManyOfYouLaunch() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    GeneralResponse generalResponse = (GeneralResponse) launchDummyBot.execute(world);
    assertEquals("Too many of you in this world", generalResponse.getMessage());
  }

  @Test
  void StateName() {
    Command test = new StateCommand();
    assertEquals("state", test.getName());
  }

  @Test
  void executeState() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    RequestObject stateObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"state\",\"arguments\":[]}");
    Command stateCommand = Command.create(stateObject);
    assertEquals(robot.getState(), stateCommand.execute(robot));
  }

  @Test
  void ExitName() {
    Command test = new ExitCommand("hal");
    assertEquals("exit", test.getName());
    assertEquals("hal", test.getArgument());
  }

  @Test
  void executeExit() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    assertEquals(1, world.getPopulation());
    RequestObject exitObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"exit\",\"arguments\":[]}");
    Command exitCommand = Command.create(exitObject);
    GeneralResponse generalResponse = (GeneralResponse) exitCommand.execute(world);
    assertEquals("Your exit sequence has cleared", generalResponse.getMessage());
    assertEquals("OK", generalResponse.getResult());
    assertEquals(0, world.getPopulation());
    assertEquals(1, world.getFugitives().size());
  }

  @Test
  void executeQuit() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    assertEquals(1, world.getPopulation());
    assertEquals(1, world.getRobotMap().size());
    RequestObject quitObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"quit\",\"arguments\":[]}");
    Command exitCommand = Command.create(quitObject);
    GeneralResponse generalResponse = (GeneralResponse) exitCommand.execute(world);
    assertEquals("Your exit sequence has cleared", generalResponse.getMessage());
    assertEquals("OK", generalResponse.getResult());
    assertEquals(0, world.getPopulation());
    assertEquals(1, world.getFugitives().size());
  }

  @Test
  void getForwardName() {
    ForwardCommand test = new ForwardCommand("hal 10");
    assertEquals("forward", test.getName());
    assertEquals("hal 10", test.getArgument());
  }

  @Test
  void executeForward() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    RequestObject forwardObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"forward\",\"arguments\":[5]}");
    Command forwardCommand = Command.create(forwardObject);
    Robot robot = world.getRobot("hal");
    Position expectedPosition =
        new Position(robot.getPosition().getX(), world.getRobot("hal").getPosition().getY() + 5);
    Response response = (Response) forwardCommand.execute(world);
    assertEquals(expectedPosition, robot.getPosition());
    Map<String, Object> data = Collections.singletonMap("message", "Done");
    assertEquals(data, response.getData());
  }

  @Test
  void getBackName() {
    BackCommand test = new BackCommand("hal 10");
    assertEquals("back", test.getName());
    assertEquals("hal 10", test.getArgument());
  }

  @Test
  void executeBack() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    RequestObject backObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"back\",\"arguments\":[5]}");
    Command backCommand = Command.create(backObject);
    Robot robot = world.getRobot("hal");
    Position expectedPosition =
        new Position(robot.getPosition().getX(), world.getRobot("hal").getPosition().getY() - 5);
    Response response = (Response) backCommand.execute(world);
    assertEquals(expectedPosition, robot.getPosition());
    Map<String, Object> data = Collections.singletonMap("message", "Done");
    assertEquals(data, response.getData());
  }

  @Test
  void getTurnRightName() {
    TurnCommand test = new TurnCommand("right");
    assertEquals("turn", test.getName());
    assertEquals("right", test.getArgument());
  }

  @Test
  void executeRightForward() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);

    RequestObject rightObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"turn\",\"arguments\":[right]}");
    Command rightCommand = Command.create(rightObject);
    Robot robot = world.getRobot("hal");
    Position expectedPosition =
        new Position(robot.getPosition().getX() + 5, world.getRobot("hal").getPosition().getY());
    Response response = (Response) rightCommand.execute(world);
    Map<String, Object> data = Collections.singletonMap("message", "Done");
    assertEquals(data, response.getData());
    assertEquals(World.Direction.EAST, robot.getCurrentDirection());
    RequestObject forwardObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"forward\",\"arguments\":[5]}");
    Command forwardCommand = Command.create(forwardObject);
    forwardCommand.execute(world);
    assertEquals(expectedPosition, robot.getPosition());
  }

  @Test
  void getTurnLeftName() {
    TurnCommand test = new TurnCommand("left");
    assertEquals("turn", test.getName());
    assertEquals("left", test.getArgument());
  }

  @Test
  void executeLeftForward() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);

    RequestObject leftObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"turn\",\"arguments\":[left]}");
    Command leftCommand = Command.create(leftObject);
    Robot robot = world.getRobot("hal");
    Position expectedPosition =
        new Position(robot.getPosition().getX() - 5, world.getRobot("hal").getPosition().getY());
    Response response = (Response) leftCommand.execute(world);
    Map<String, Object> data = Collections.singletonMap("message", "Done");
    assertEquals(data, response.getData());
    assertEquals(World.Direction.WEST, robot.getCurrentDirection());
    RequestObject forwardObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"forward\",\"arguments\":[5]}");
    Command forwardCommand = Command.create(forwardObject);
    forwardCommand.execute(world);
    assertEquals(expectedPosition, robot.getPosition());
  }

  @Test
  void repairName() {
    Command test = new RepairCommand();
    assertEquals("repair", test.getName());
  }

  @Test
  void executeRepair() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    RequestObject repairObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"repair\",\"arguments\":[]}");
    Command repairCommand = Command.create(repairObject);
    int repairTime = world.getRepairTime();
    long start = System.currentTimeMillis();
    repairCommand.execute(robot);
    long end = System.currentTimeMillis();
    assertEquals(World.Mode.REPAIR, robot.getStatus());
    assertEquals(repairTime, (end - start) / 1000);
  }

  @Test
  void reloadName() {
    Command test = new ReloadCommand();
    assertEquals("reload", test.getName());
  }

  @Test
  void executeReload() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    RequestObject reloadObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"reload\",\"arguments\":[]}");
    Command reloadCommand = Command.create(reloadObject);
    int reloadTime = world.getReloadTime();
    long start = System.currentTimeMillis();
    reloadCommand.execute(robot);
    long end = System.currentTimeMillis();
    assertEquals(World.Mode.RELOAD, robot.getStatus());
    assertEquals(reloadTime, (end - start) / 1000);
  }

  @Test
  void mineName() {
    Command test = new MineCommand("hal");
    assertEquals("mine", test.getName());
    assertEquals("hal", test.getArgument());
  }

  @Test
  void executeMine() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    Position expectedPosition =
        new Position(robot.getPosition().getX(), world.getRobot("hal").getPosition().getY() + 1);
    RequestObject mineObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"mine\",\"arguments\":[]}");
    Command mineCommand = Command.create(mineObject);
    int mineTime = world.getMineSet();
    long start = System.currentTimeMillis();
    mineCommand.execute(world);
    long end = System.currentTimeMillis();
    assertEquals(World.Mode.SETMINE, robot.getStatus());
    assertEquals(mineTime, (end - start) / 1000);
    assertEquals(expectedPosition, robot.getPosition());
  }

  @Test
  void FireName() {
    Command test = new FireCommand("hal");
    assertEquals("fire", test.getName());
    assertEquals("hal", test.getArgument());
  }

  @Test
  void executeFireMiss() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    Integer shots = robot.getShots() - 1;
    Map<String, Object> data = Collections.singletonMap("message", "Miss");
    Map<String, Object> state = Collections.singletonMap("shots", shots);
    RequestObject fireObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"fire\",\"arguments\":[]}");
    Command fireCommand = Command.create(fireObject);
    Response response = (Response) fireCommand.execute(world);
    assertEquals(data, response.getData());
    assertEquals(state, response.getState());
  }

  @Test
  void LookName() {
    Command test = new LookCommand("hal");
    assertEquals("look", test.getName());
    assertEquals("hal", test.getArgument());
  }

  @Test
  void executeLook() {
    World world = new World(200, 400, 10, 7, 5, 9, 3);
    world.setMaze(new OpenFieldMaze(world));
    RequestObject requestObject =
        new RequestObject(
            "{\"robot\":\"hal\",\"command\":\"launch\",\"arguments\":[dummy, 10, 20]}");
    Command launchDummyBot = Command.create(requestObject);
    launchDummyBot.execute(world);
    Robot robot = world.getRobot("hal");
    RequestObject lookObject =
        new RequestObject("{\"robot\":\"hal\",\"command\":\"look\",\"arguments\":[]}");
    Command lookCommand = Command.create(lookObject);
    Response response = (Response) lookCommand.execute(world);
    Map<String, Object> state = Collections.singletonMap("state", robot.getState());
    assertEquals(robot.getLookerList(), response.getData().get("objects"));
    assertEquals(state, response.getState());
  }
}
