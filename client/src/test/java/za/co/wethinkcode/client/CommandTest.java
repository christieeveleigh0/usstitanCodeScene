package za.co.wethinkcode.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommandTest {
  public static Socket clientSocket;
  public TestServer test = new TestServer();
  public Robot robot = new Robot("dummy", "hal", 0, 0);
  public Comms comms;
  public Colour colour = new Colour();

  @BeforeEach
  public void setup() throws IOException, InterruptedException {

    Thread task = new Thread(test);
    task.start();
    TimeUnit.SECONDS.sleep(
        1); // THIS ALLOWS THE SERVER A SECOND TO STARTUP BEFORE WE CONNECT TO IT.
    clientSocket = new Socket("localhost", 9999);
    comms = new Comms(clientSocket);
  }

  @Test
  @Order(1)
  void executeLaunch() {
    Command launch = Command.create("launch dummy hal");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "launch");
      request.putArray("arguments").add("dummy").add(0).add(0);
      assertTrue(launch.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(
        colour.green(
            ">>> Launching dummy-hal at position [0,0].\n"
                + ">>> Initialization report.\n"
                + "\tDirection:        \"NORTH\"\n"
                + "\tShields capacity: 0\n"
                + "\tShots capacity:   0"),
        robot.getOutput());
  }

  @Test
  @Order(2)
  void executeState() {
    Command state = Command.create("state");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "state");
      request.putArray("arguments");
      assertTrue(state.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(
        colour.green(
            ">>> dummy-hal's Diagnostics report.\n"
                + "\tPosition:     [0,0]\n"
                + "\tDirection:    \"NORTH\"\n"
                + "\tShields left: 3\n"
                + "\tShots left:   2"),
        robot.getOutput());
  }

  @Test
  @Order(3)
  void executeLook() {
    Command look = Command.create("look");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "look");
      request.putArray("arguments");
      assertTrue(look.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(
        colour.green(
            ">>> This is what dummy-hal can see:\n"
                + "**************************\n"
                + "Object:    OBSTACLE.\n"
                + "Direction: NORTH.\n"
                + "Distance:  5 steps.\n"
                + "**************************\n"
                + "Object:    PIT.\n"
                + "Direction: EAST.\n"
                + "Distance:  3 steps.\n"
                + "**************************\n"
                + "Object:    ROBOT.\n"
                + "Direction: WEST.\n"
                + "Distance:  1 steps."),
        robot.getOutput());
  }

  @Test
  @Order(4)
  void executeForward() {
    Command forward10 = Command.create("forward 10");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "forward");
      request.putArray("arguments").add(10);
      assertTrue(forward10.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(
        colour.green(">>> dummy-hal Moved forward 10\n" + "   Now at position [10,0]."),
        robot.getOutput());
  }

  @Test
  @Order(5)
  void executeBack() {
    Command back10 = Command.create("back 10");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "back");
      request.putArray("arguments").add(10);
      assertTrue(back10.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(
        colour.green(">>> dummy-hal Moved back 10\n" + "   Now at position [-10,0]."),
        robot.getOutput());
  }

  @Test
  @Order(6)
  void executeRight() {
    Command right = Command.create("turn right");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "turn");
      request.putArray("arguments").add("right");
      assertTrue(right.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.green(">>> dummy-hal Turned right."), robot.getOutput());
  }

  @Test
  @Order(7)
  void executeLeft() {
    Command left = Command.create("turn left");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "turn");
      request.putArray("arguments").add("left");
      assertTrue(left.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.green(">>> dummy-hal Turned left."), robot.getOutput());
  }

  @Test
  @Order(8)
  void executeRepair() {
    Command look = Command.create("repair");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "repair");
      request.putArray("arguments");
      assertTrue(look.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.green(">>> dummy-hal repaired in 0 seconds..."), robot.getOutput());
  }

  @Test
  @Order(9)
  void executeQuit() {
    Command quit = Command.create("quit");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "quit");
      request.putArray("arguments");
      assertFalse(quit.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.purple("<T11101> Time to leave this place."), robot.getOutput());
  }

  @Test
  @Order(10)
  void executeReload() {
    Command reload = Command.create("reload");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "reload");
      request.putArray("arguments");
      assertTrue(reload.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.green(">>> dummy-hal reloaded in 0 seconds."), robot.getOutput());
  }

  @Test
  @Order(11)
  void executeMine() {
    Command mine = Command.create("mine");
    Robot robot = new Robot("bomber", "hal", 0, 0);
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "mine");
      request.putArray("arguments");
      assertTrue(mine.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.green(">>> bomber-hal placed a mine in 0 seconds."), robot.getOutput());
  }

  @Test
  @Order(12)
  void executeFire() {
    Command fire = Command.create("fire");
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode request = mapper.createObjectNode();
      request.put("robot", "hal");
      request.put("command", "fire");
      request.putArray("arguments");
      assertTrue(fire.execute(robot, comms));
      assertEquals(request, comms.getRequest());

    } catch (Exception ignored) {
    }

    assertEquals(colour.green(">>> Shots fired!\n" + "   You hit HALBOT."), robot.getOutput());
  }

  @Test
  @Order(13)
  void executeHelp() {
    Command help = Command.create("help");
    assertTrue(help.execute(robot, comms));
    assertEquals(
        colour.green(
            "# See Wiki for detailed info -"
                + " https://github.com/wtc-cohort-2020/0029-robot-worlds/wiki/Robot-types-and-Commands.\n\n"
                + "------------------------------ Command List"
                + " -----------------------------------\n"
                + "-------------------------------------------------------------------------------\n"
                + ": [HELP]           displays what you're reading right now\n"
                + ": [QUIT]           exits the world\n"
                + ": [LAUNCH]         send specific robot into the world e.g. 'LAUNCH SNIPER"
                + " VUYO'\n"
                + ": [FORWARD]        move forward by specified number of steps, e.g. 'FORWARD"
                + " 10'\n"
                + ": [BACK]           move back by specified number of steps, e.g. 'BACK 10'\n"
                + ": [LEFT]           turn left\n"
                + ": [RIGHT]          turn right\n"
                + ": [LOOK]           look around in all four directions\n"
                + ": [REPAIR]         repairs the robot's shields to maximum strength\n"
                + ": [FIRE]           fires the robot's gun\n"
                + ": [RELOAD]         reloads the robot's gun\n"
                + ": [MINE]           places a mine\n"
                + "-------------------------------------------------------------------------------\n"
                + "-------------------------------------------------------------------------------\n"),
        robot.getOutput());
  }

  @Test
  @Order(14)
  void createCommand() {
    Command help = Command.create("help");
    assertEquals("help", help.getName());

    Command quit = Command.create("quit");
    assertEquals("quit", quit.getName());

    Command launchDummyBot = Command.create("launch dummy hal");
    assertEquals("launch", launchDummyBot.getName());
    assertEquals("dummy hal", launchDummyBot.getArgument());

    Command forward = Command.create("forward 10");
    assertEquals("forward", forward.getName());
    assertEquals("10", forward.getArgument());

    Command back = Command.create("back 10");
    assertEquals("back", back.getName());
    assertEquals("10", back.getArgument());

    Command left = Command.create("turn left");
    assertEquals("turn", left.getName());
    assertEquals("left", left.getArgument());

    Command right = Command.create("turn right");
    assertEquals("turn", right.getName());
    assertEquals("right", right.getArgument());

    Command look = Command.create("look");
    assertEquals("look", look.getName());

    Command repair = Command.create("repair");
    assertEquals("repair", repair.getName());

    Command fire = Command.create("fire");
    assertEquals("fire", fire.getName());

    Command reload = Command.create("reload");
    assertEquals("reload", reload.getName());

    Command mine = Command.create("mine");
    assertEquals("mine", mine.getName());
  }

  private static class TestServer implements Runnable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      clientSocket = serverSocket.accept();
      ObjectMapper mapper = new ObjectMapper();
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      JsonNode request = mapper.readTree(in.readLine());
      switch (request.get("command").asText()) {
        case "launch":
          out.println(
              "{\"result\":\"OK\",\"data\":{\"mine\":9,\"repair\":7,\"shields\":3,\"reload\":5,\"visibility\":10,\"position\":[0,0]},\"state\":{\"shields\":0,\"position\":[0,0],\"shots\":0,\"direction\":\"NORTH\",\"status\":\"NORMAL\"}}");
          break;
        case "state":
          out.println(
              "{\"position\": [0,0],\"direction\": \"NORTH\",\"shields\": 3,\"shots\":"
                  + " 2,\"status\": \"NORMAL\"}");
        case "forward":
          out.println(
              "{\"result\":\"OK\",\"data\":{\"message\":\"Done\"},\"state\":{\"position\":[10,0]}}");
          break;
        case "back":
          out.println(
              "{\"result\" : \"OK\", "
                  + "\"data\" : {"
                  + "\"message\" : \"Done\"},"
                  + "\"state\" : {"
                  + "\"position\": [-10,0]}}");
          break;
        case "turn":
          out.println(
              "{\"result\": \"OK\",\"data\": {\"message\": \"Done\"},\"state\": {\"direction\":"
                  + " \"EAST\"}}");
          break;
        case "look":
          out.println(
              "{"
                  + "  \"result\": \"OK\","
                  + "  \"data\": {"
                  + "  \"objects\" : ["
                  + "       {"
                  + "           \"direction\" : \"NORTH\","
                  + "           \"type\" : \"OBSTACLE\","
                  + "           \"distance\" : 5"
                  + "       },"
                  + "       {"
                  + "           \"direction\" : \"EAST\","
                  + "           \"type\" : \"PIT\","
                  + "           \"distance\" : 3"
                  + "       },"
                  + "       {"
                  + "           \"direction\" : \"WEST\","
                  + "           \"type\" : \"ROBOT\","
                  + "           \"distance\" : 1"
                  + "       }"
                  + "       ]"
                  + "   },"
                  + "  \"state\": {"
                  + "    \"position\": [0,0],"
                  + "    \"direction\": \"NORTH\","
                  + "    \"shields\": 3,"
                  + "    \"shots\": 2,"
                  + "    \"status\": \"NORMAL\""
                  + "   }"
                  + "}");
          break;
        case "repair":
          out.println(
              "{"
                  + "  \"result\": \"OK\","
                  + "\"data\" : {"
                  + "\"message\" : \"Done\"},"
                  + "  \"state\": {"
                  + "    \"position\": [0,0],"
                  + "    \"direction\": \"NORTH\","
                  + "    \"shields\": 3,"
                  + "    \"shots\": 2,"
                  + "    \"status\": \"REPAIR\""
                  + "   }"
                  + "}");
          break;
        case "fire":
          out.println(
              "{"
                  + "  \"result\": \"OK\","
                  + "\"data\" : {"
                  + "\"message\" : \"Hit\", "
                  + "\"distance\" : 3, "
                  + "\"robot\" : \"HALBOT\"},"
                  + "  \"state\": {"
                  + "    \"position\": [0,0],"
                  + "    \"direction\": \"NORTH\","
                  + "    \"shields\": 3,"
                  + "    \"shots\": 2,"
                  + "    \"status\": \"SETMINE\""
                  + "   }"
                  + "}");
          break;
        case "reload":
          out.println(
              "{"
                  + "  \"result\": \"OK\","
                  + "\"data\" : {"
                  + "\"message\" : \"Done\"},"
                  + "  \"state\": {"
                  + "    \"position\": [0,0],"
                  + "    \"direction\": \"NORTH\","
                  + "    \"shields\": 3,"
                  + "    \"shots\": 2,"
                  + "    \"status\": \"RELOAD\""
                  + "   }"
                  + "}");
          break;
        case "mine":
          out.println(
              "{"
                  + "  \"result\": \"OK\","
                  + "\"data\" : {"
                  + "\"message\" : \"Done\"},"
                  + "  \"state\": {"
                  + "    \"position\": [0,0],"
                  + "    \"direction\": \"NORTH\","
                  + "    \"shields\": 3,"
                  + "    \"shots\": 2,"
                  + "    \"status\": \"SETMINE\""
                  + "   }"
                  + "}");
          break;
      }
    }

    public void stop() throws IOException {
      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
    }

    @Override
    public void run() {
      TestServer server = new TestServer();
      try {
        server.start(9999);
        server.stop();
      } catch (IOException ignored) {
      }
    }
  }
}
